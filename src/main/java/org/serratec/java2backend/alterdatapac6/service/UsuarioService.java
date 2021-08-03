package org.serratec.java2backend.alterdatapac6.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.serratec.java2backend.alterdatapac6.config.MailConfig;
import org.serratec.java2backend.alterdatapac6.dto.UsuarioDtoRequest;
import org.serratec.java2backend.alterdatapac6.dto.UsuarioDtoResponse;
import org.serratec.java2backend.alterdatapac6.entity.EquipeEntity;
import org.serratec.java2backend.alterdatapac6.entity.ImagemEntity;
import org.serratec.java2backend.alterdatapac6.entity.PapelEntity;
import org.serratec.java2backend.alterdatapac6.entity.StatusEntity;
import org.serratec.java2backend.alterdatapac6.entity.UsuarioEntity;
import org.serratec.java2backend.alterdatapac6.exceptions.UsuarioNotFoundException;
import org.serratec.java2backend.alterdatapac6.mapper.UsuarioMapper;
import org.serratec.java2backend.alterdatapac6.repository.EquipeRepository;
import org.serratec.java2backend.alterdatapac6.repository.ImagemRepository;
import org.serratec.java2backend.alterdatapac6.repository.PapelRepository;
import org.serratec.java2backend.alterdatapac6.repository.StatusRepository;
import org.serratec.java2backend.alterdatapac6.repository.UsuarioRepository;
import org.serratec.java2backend.alterdatapac6.util.GeraSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javassist.NotFoundException;

@Service
public class UsuarioService {

	@Autowired
	GeraSenha geraSenha;

	@Autowired
	UsuarioRepository repository;

	@Autowired
	UsuarioMapper mapper;

	@Autowired
	PapelRepository papelRepository;

	@Autowired
	StatusRepository statusRepository;

	@Autowired
	EquipeRepository equipeRepository;

	@Autowired
	ImagemService imagemService;

	@Autowired
	BCryptPasswordEncoder bCrypt;

	@Autowired
	MailConfig mailConfig;

	public List<UsuarioDtoResponse> getAllUser() throws NotFoundException {
		List<UsuarioEntity> listEntity = repository.findAll();
		List<UsuarioDtoResponse> listDto = new ArrayList();

		for (UsuarioEntity entity : listEntity) {
			UsuarioDtoResponse dto = getByUserName(entity.getUserName());
			listDto.add(dto);
		}

		return listDto;
	}

	public UsuarioDtoResponse getByUserName(String userName) throws NotFoundException {

		UsuarioEntity entity = repository.getByUserName(userName);

		if (entity == null) {
			throw new NotFoundException("Usuario não encontrado");
		}

		UsuarioDtoResponse usuarioDto = mapper.toDto(entity);
		String uri = adicionaUrlImagem(entity);
		usuarioDto.setUrl(uri);

		return usuarioDto;

	}

	public String adicionaUrlImagem(UsuarioEntity entity) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/usuario/{usuarioId}/image")
				.buildAndExpand(entity.getId()).toUri();
		return uri.toString();
	}

	/*
	 * public UsuarioDtoResponse getByUserNameUrl(String userName) { UsuarioEntity
	 * entity = repository.getByUserName(userName); UsuarioDtoResponse dto =
	 * mapper.toDto(entity); return dto; }
	 */

	// 02/08/21 criação de metodo para controlar alteração de perfil

	/*
	 * public UsuarioDtoResponse verificaPerfil(String userName, UsuarioDtoRequest
	 * dto, MultipartFile file) throws IOException, NotFoundException {
	 * 
	 * if (userName.equals(dto.getUserName())) { return editaPerfilN1(dto, file); }
	 * 
	 * UsuarioEntity usuarioHist = repository.getByUserName(userName); if
	 * (usuarioHist.getEquipe().equals(dto.getEquipe())) { return
	 * editaPerfilN2(dto); }
	 * 
	 * return editaPerfilN3(userName, dto);
	 * 
	 * }
	 */

	// 02/08/21 esse metodo pega a equipe do usuario ativo e replica para o usuario
	// de outra equipe que terá o perfil alterado
	public UsuarioDtoResponse editaPerfilN3(String userName, UsuarioDtoRequest dto) {

		UsuarioEntity histUserName = repository.getByUserName(userName);
		UsuarioEntity histDto = repository.getByUserName(dto.getUserName());

		EquipeEntity equipe = new EquipeEntity();
		equipe = histUserName.getEquipe();
		histDto.setEquipe(equipe);

		return mapper.toDto(repository.save(histDto));

	}

	// 02/08/21 esse metodo altera alguns atributos do usuario da mesma equipe
	// recebido como parametro
	public UsuarioDtoResponse editaPerfilN2(String userName, UsuarioDtoRequest dto) {

		UsuarioEntity histUserName = repository.getByUserName(userName);

		UsuarioEntity histDto = repository.getByUserName(dto.getUserName());
		if (dto.getNickName() != null) {
			histDto.setNickName(dto.getNickName());
		}

		if (dto.getPapel() != null) {
			PapelEntity papel = new PapelEntity();
			papel = papelRepository.getByNome(dto.getPapel());
			histDto.setPapel(papel);

		}

		if (dto.getEquipe() != null) {
			EquipeEntity equipe = new EquipeEntity();
			equipe = equipeRepository.getByNome(dto.getEquipe());
			histDto.setEquipe(equipe);
		}

		if (dto.getStatus() != null) {
			StatusEntity status = new StatusEntity();
			status = statusRepository.getByNome(dto.getStatus());
			histDto.setStatus(status);
		}
		return mapper.toDto(repository.save(histDto));

	}

	// 01/08/21 metodo criado para editar perfil do usuário recebe dto e uma imagem
	// e devolve um usuario response
	public UsuarioDtoResponse editaPerfilN1(String userName, UsuarioDtoRequest dto, MultipartFile file)
			throws IOException, NotFoundException {

		UsuarioEntity usuarioEditado = updateN1(userName, dto);

		Long usuarioId = usuarioEditado.getId();

		ImagemEntity imagem = imagemService.getImagem(usuarioId);
		if (imagem != null) {
			imagemService.deleteById(imagem.getId());
		}

		imagemService.create(usuarioEditado, file);

		return getByUserName(usuarioEditado.getUserName());

	}

	// 01/08/21 -- modifiquei o retorno do método para ser compatível com
	// editaPerfil(), que recebe um entity
	public UsuarioEntity updateN1(String userName, UsuarioDtoRequest usuario) {
		UsuarioEntity usuarioHist = repository.getByUserName(userName);

		if (usuario.getNome() != null) {
			usuarioHist.setNome(usuario.getNome());
		}

		if (usuario.getNickName() != null) {
			usuarioHist.setNickName(usuario.getNickName());
		}

		if (usuario.getPapel() != null) {
			PapelEntity papel = new PapelEntity();
			papel = papelRepository.getByNome(usuario.getPapel());
			usuarioHist.setPapel(papel);

		}

		if (usuario.getUserName() != null) {
			usuarioHist.setUserName(usuario.getUserName());
		}

		if (usuario.getPassword() != null) {
			usuarioHist.setPassword(bCrypt.encode(usuario.getPassword()));
		}

		if (usuario.getEquipe() != null) {
			EquipeEntity equipe = new EquipeEntity();
			equipe = equipeRepository.getByNome(usuario.getEquipe());
			usuarioHist.setEquipe(equipe);
		}

		if (usuario.getStatus() != null) {
			StatusEntity status = new StatusEntity();
			status = statusRepository.getByNome(usuario.getStatus());
			usuarioHist.setStatus(status);
		}

		if (usuario.getEmail() != null) {
			usuarioHist.setEmail(usuario.getEmail());
		}

		if (usuario.getDtNascimento() != null) {
			usuarioHist.setDtNascimento(usuario.getDtNascimento());
		}

		// return mapper.toDto(repository.save(usuarioHist));
		return repository.save(usuarioHist);

	}

	public String deleteByUserName(String userName) {

		UsuarioEntity usuario = repository.getByUserName(userName);
		if (usuario != null) {
			Long usuarioId = usuario.getId();

			ImagemEntity imagem = imagemService.getImagem(usuarioId);
			imagemService.deleteById(imagem.getId());
			repository.deleteById(usuarioId);
			return userName;

		} else {
			return "";
		}

	}

	public String criaPerfil(String userName,UsuarioDtoRequest usuario) throws NotFoundException, MessagingException {
		
		UsuarioEntity usuarioNovo = mapper.toEntity(usuario);

		String senhaNova, NewUserName, nome, padraoUser = "alterdata.";
		Integer posicaoEspaco;
		
		// gera uma senha aleatória
		senhaNova = geraSenha.geradorSenha(); // "ResetPac62021";
		usuarioNovo.setPassword(bCrypt.encode(senhaNova));

		// cria o userName a partir do nome
		nome = usuario.getNome().toLowerCase();
		posicaoEspaco = nome.indexOf(" ");
		NewUserName = padraoUser + nome.substring(0, posicaoEspaco)+senhaNova.substring(0, 4);
		usuarioNovo.setUserName(NewUserName);
		
		// capatura a imagem do usuario admim para servir como imagem inical do perfil
		
		
		/*
		 * UsuarioEntity usuarioAdmin = repository.getByUserName("alterdata.admin");
		 * ImagemEntity imagemAdmin = usuarioAdmin.getImagem();
		 * usuarioNovo.setImagem(imagemAdmin);
		 */
		// usuarioNovo.setNickName(null);
		// 03/08/2021 -- preenche alguns parâmetros do usuário novo
		UsuarioEntity entity = repository.getByUserName(userName);
		//EquipeEntity equipe = equipeRepository.getByNome(userName);
		usuarioNovo.setEquipe(entity.getEquipe());
		usuarioNovo.setPapel(entity.getPapel());
		usuarioNovo.setStatus(entity.getStatus());

		repository.save(usuarioNovo);

		return resetSenha(NewUserName);

	}

	public UsuarioEntity createUsuario(UsuarioDtoRequest usuario) {
		UsuarioEntity usuarioNovo = mapper.toEntity(usuario);

		PapelEntity papel = papelRepository.getByNome(usuario.getPapel());
		usuarioNovo.setPapel(papel);

		StatusEntity status = statusRepository.getByNome(usuario.getStatus());
		usuarioNovo.setStatus(status);

		EquipeEntity equipe = equipeRepository.getByNome(usuario.getEquipe());
		usuarioNovo.setEquipe(equipe);

		usuarioNovo.setPassword(bCrypt.encode(usuarioNovo.getPassword()));
		return repository.save(usuarioNovo);

	}

	// cria o usuario e retorna um UsuarioDtoResponse
	public UsuarioDtoResponse create(UsuarioDtoRequest dto, MultipartFile file) throws IOException, NotFoundException {

		UsuarioEntity entitySaved = createUsuario(dto);
		imagemService.create(entitySaved, file);

		return getByUserName(entitySaved.getUserName());
	}

	public String resetSenha(String userName) throws MessagingException {

		UsuarioEntity entity = repository.getByUserName(userName);
		String email, senhaNova, usuario;
		email = entity.getEmail();
		usuario = entity.getUserName();
		senhaNova = geraSenha.geradorSenha(); // "ResetPac62021";
		entity.setPassword(bCrypt.encode(senhaNova));
		repository.save(entity);

		String subject = entity.getNome() + " - AlterDevs message";

		String body = "<tr><td>" + usuario + "</td><td></td>" + "<td>" + senhaNova + "</td><td></td><td></tr>";

		String msg = "<table>" + "<thead style=color:blue>" + "<td><b>UserName</b></td><td></td>"
				+ "<td><b>Password</b></td><td></td>" + "</thead>" + "<tbody>" + body + "</tbody>" + "</table>";

		return mailConfig.sendEmail(email, subject, msg);

	}

	// =========================================Tere
	// 29/07/21=======================================

	public UsuarioDtoResponse getByUserNameDto(String userName) {
		UsuarioEntity entity = repository.getByUserName(userName);

		UsuarioDtoResponse usuarioDto = mapper.toDto(entity);

		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/usuario/{usuarioId}/image")
				.buildAndExpand(entity.getId()).toUri();
		usuarioDto.setUrl(uri.toString());

		return usuarioDto;
	}

}
