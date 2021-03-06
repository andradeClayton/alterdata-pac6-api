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
import org.serratec.java2backend.alterdatapac6.exceptions.UsuarioDuplicadoException;
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

	public List<UsuarioDtoResponse> getAllUser() throws UsuarioNotFoundException {
		List<UsuarioEntity> listEntity = repository.findAll();
		List<UsuarioDtoResponse> listDto = new ArrayList();

		for (UsuarioEntity entity : listEntity) {
			UsuarioDtoResponse dto = getByUserName(entity.getUserName());
			listDto.add(dto);
		}

		return listDto;
	}

	public UsuarioDtoResponse getByUserName(String userName) throws UsuarioNotFoundException {

		UsuarioEntity entity = repository.getByUserName(userName);

		if (entity == null) {
			throw new UsuarioNotFoundException("Usuario " + userName + " n??o encontrado");
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

	// 02/08/21 cria????o de metodo para controlar altera????o de perfil

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
	// de outra equipe que ter?? o perfil alterado
	public UsuarioDtoResponse editaPerfilN3(String userName, UsuarioDtoRequest dto) throws UsuarioNotFoundException {

		UsuarioEntity histUserName = repository.getByUserName(userName);
		UsuarioEntity histDto = repository.getByUserName(dto.getUserName());

		if (histUserName == null) {
			throw new UsuarioNotFoundException("Usuario " + userName + " n??o encontrado");
		}

		if (histDto == null) {
			throw new UsuarioNotFoundException("Usuario " + dto.getUserName() + " n??o encontrado");
		}

		EquipeEntity equipe = new EquipeEntity();
		equipe = histUserName.getEquipe();
		histDto.setEquipe(equipe);

		return mapper.toDto(repository.save(histDto));

	}

	// 02/08/21 esse metodo altera alguns atributos do usuario da mesma equipe
	// recebido como parametro
	public UsuarioDtoResponse editaPerfilN2(String userName, UsuarioDtoRequest dto) throws UsuarioNotFoundException {

		UsuarioEntity histUserName = repository.getByUserName(userName);

		UsuarioEntity histDto = repository.getByUserName(dto.getUserName());

		if (histUserName == null) {
			throw new UsuarioNotFoundException("Usuario " + userName + " n??o encontrado");
		}

		if (histDto == null) {
			throw new UsuarioNotFoundException("Usuario " + dto.getUserName() + " n??o encontrado");
		}

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

	// 01/08/21 metodo criado para editar perfil do usu??rio recebe dto e uma imagem
	// e devolve um usuario response
	public UsuarioDtoResponse editaPerfilN1(String userName, UsuarioDtoRequest dto, MultipartFile file)
			throws IOException, UsuarioNotFoundException, UsuarioDuplicadoException {

		UsuarioEntity usuarioEditado = updateN1(userName, dto);

		Long usuarioId = usuarioEditado.getId();

		ImagemEntity imagem = imagemService.getImagem(usuarioId);
		if (imagem != null) {
			imagemService.deleteById(imagem.getId());
		}

		imagemService.create(usuarioEditado, file);

		return getByUserName(usuarioEditado.getUserName());

	}

	// 01/08/21 -- modifiquei o retorno do m??todo para ser compat??vel com
	// editaPerfil(), que recebe um entity
	public UsuarioEntity updateN1(String userName, UsuarioDtoRequest usuario) throws UsuarioNotFoundException, UsuarioDuplicadoException {
		UsuarioEntity usuarioHist = repository.getByUserName(userName);
		
		if (usuarioHist == null) {
			throw new UsuarioNotFoundException("Usuario " + userName + " n??o encontrado");
		}

		UsuarioEntity usuarioBd = repository.getByUserName(usuario.getUserName());
			
		if(!userName.equals(usuario.getUserName())) {
			
			if(usuarioBd!=null) {
					throw new UsuarioDuplicadoException("O usu??rio "+usuario.getUserName()+" j?? existe. Escolha outro username");
			}
			
		}
				
		
		
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

	public String deleteByUserName(String userName) throws UsuarioNotFoundException {

		UsuarioEntity usuario = repository.getByUserName(userName);
		if (usuario == null) {
			throw new UsuarioNotFoundException("Usuario " + userName + " n??o encontrado");
		}

		if (usuario != null) {
			Long usuarioId = usuario.getId();

			ImagemEntity imagem = imagemService.getImagem(usuarioId);
			if(imagem!=null) {
				imagemService.deleteById(imagem.getId());
			}
							
			repository.deleteById(usuarioId);
			return userName;

		} else {
			return "";
		}

	}

	public String criaPerfil(String userName, UsuarioDtoRequest usuario)
			throws MessagingException, UsuarioNotFoundException {

		UsuarioEntity usuarioNovo = mapper.toEntity(usuario);

		UsuarioEntity entity = repository.getByUserName(userName);
		if (entity == null) {
			throw new UsuarioNotFoundException("Usuario " + userName + " n??o encontrado");
		}

		UsuarioEntity usuarioHist = repository.getByUserName(usuario.getUserName());

		String senhaNova, NewUserName, nome, padraoUser = "alterdata.";
		Integer posicaoEspaco;

		// gera uma senha aleat??ria
		senhaNova = geraSenha.geradorSenha(); // "ResetPac62021";
		usuarioNovo.setPassword(bCrypt.encode(senhaNova));

		// cria o userName a partir do nome
		nome = usuario.getNome().toLowerCase();
		posicaoEspaco = nome.indexOf(" ");
		NewUserName = padraoUser + nome.substring(0, posicaoEspaco) + senhaNova.substring(0, 4);
		usuarioNovo.setUserName(NewUserName);

		// 03/08/2021 -- preenche alguns par??metros do usu??rio novo

		usuarioNovo.setEquipe(entity.getEquipe());
		usuarioNovo.setPapel(entity.getPapel());
		usuarioNovo.setStatus(entity.getStatus());

		repository.save(usuarioNovo);

		return resetSenha(NewUserName);

	}

	/*
	 * public UsuarioEntity createUsuario(UsuarioDtoRequest usuario) { UsuarioEntity
	 * usuarioNovo = mapper.toEntity(usuario);
	 * 
	 * PapelEntity papel = papelRepository.getByNome(usuario.getPapel());
	 * usuarioNovo.setPapel(papel);
	 * 
	 * StatusEntity status = statusRepository.getByNome(usuario.getStatus());
	 * usuarioNovo.setStatus(status);
	 * 
	 * EquipeEntity equipe = equipeRepository.getByNome(usuario.getEquipe());
	 * usuarioNovo.setEquipe(equipe);
	 * 
	 * usuarioNovo.setPassword(bCrypt.encode(usuarioNovo.getPassword())); return
	 * repository.save(usuarioNovo);
	 * 
	 * }
	 * 
	 * // cria o usuario e retorna um UsuarioDtoResponse public UsuarioDtoResponse
	 * create(UsuarioDtoRequest dto, MultipartFile file) throws IOException,
	 * NotFoundException, UsuarioNotFoundException {
	 * 
	 * UsuarioEntity entitySaved = createUsuario(dto);
	 * imagemService.create(entitySaved, file);
	 * 
	 * return getByUserName(entitySaved.getUserName()); }
	 */

	public String resetSenha(String userName) throws MessagingException, UsuarioNotFoundException {

		UsuarioEntity entity = repository.getByUserName(userName);
		if (entity == null) {
			throw new UsuarioNotFoundException("Usuario " + userName + " n??o encontrado");
		}

		String email, senhaNova, usuario;
		email = entity.getEmail();
		usuario = entity.getUserName();
		senhaNova = geraSenha.geradorSenha(); // "ResetPac62021";
		entity.setPassword(bCrypt.encode(senhaNova));
		repository.save(entity);

		String subject = entity.getNome() + " - AlterDevs message";

		/*
		 * String body = "<tr><td>" + usuario + "</td><td></td>" + "<td>" + senhaNova +
		 * "</td><td></td><td></tr>";
		 * 
		 * String msg = "<table>" + "<thead style=color:blue>" +
		 * "<td><b>UserName</b></td><td></td>" + "<td><b>Password</b></td><td></td>" +
		 * "</thead>" + "<tbody>" + body + "</tbody>" + "</table>";
		 */
		
		/*
		 * String msg= "<h1> Ol?? " + usuario+ ",  tudo bem? </h1>"
		 * +"<p>Voc?? enviou uma solita????o para redefinir a sua senha. Abaixo segue a sua nova senha:</p>"
		 * + "<p style=color:blue>Nova senha: " + senhaNova + "</p>" +
		 * "<p>Fa??a o login com a nova senha. Lembre-se que ?? poss??vel fazer a altera????o desta senha nas configura????es do seu perfil. </p>"
		 * ;
		 */
		
		String msg= "<div style=text-align:center borde: solid 2px>"+
				"<h2 style= color:#fff background-color:#03569C> Ol?? " + entity.getNome()+ ", tudo bem? </h2>" + 
				"<p>Voc?? enviou uma solita????o para redefinir a sua senha, segue abaixo os novos dados de acesso.</p>" +
                "<p><b>Usu??rio: </b>" + userName + "</p>" +
                "<p style=color:#03569C><b>Nova Senha: </b>" + senhaNova + "</p>" +
                "<p>Lembre-se voc?? tamb??m pode altera????o sua senha nas configura????es do seu perfil. </p>"+
                "<img style= height:80px width:120px src='https://images-ext-2.discordapp.net/external/hxjmuC-LoXcogsUgbRNLdryurFZrZStJCogsbKR1M6o/https/www.canva.com/design/DAEmMD7CT7M/screen?width=400&height=175'></img>"+
                "</div>";
		
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
