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
import org.serratec.java2backend.alterdatapac6.entity.PapelEntity;
import org.serratec.java2backend.alterdatapac6.entity.StatusEntity;
import org.serratec.java2backend.alterdatapac6.entity.UsuarioEntity;
import org.serratec.java2backend.alterdatapac6.mapper.UsuarioMapper;
import org.serratec.java2backend.alterdatapac6.repository.EquipeRepository;
import org.serratec.java2backend.alterdatapac6.repository.PapelRepository;
import org.serratec.java2backend.alterdatapac6.repository.StatusRepository;
import org.serratec.java2backend.alterdatapac6.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UsuarioService {
	
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
	
	/*
	  antes de incluir a url da imagem */
	public List<UsuarioEntity> getAll() {

		return repository.findAll();
	}
	 
	 
	 
	
	/*
	 * public List<UsuarioDto> getAll() { List<UsuarioEntity> listEntity =
	 * repository.findAll(); List<UsuarioDto> listDto = new ArrayList(); UsuarioDto
	 * usuarioDto; for(UsuarioEntity entity:listEntity) { //String userName =
	 * entity.getUserName(); //usuarioDto = getByUserNameUrl(userName); inclui a url
	 * da imagem, comentado até consertar o erro do heroku usuarioDto =
	 * mapper.toDto(entity); listDto.add(usuarioDto); }
	 * 
	 * return listDto; }
	 */
	

	public UsuarioEntity getByUserName(String useName) {
		return repository.getByUserName(useName);
		
		
	}
	
	public UsuarioDtoResponse getByUserNameUrl(String userName) {
		UsuarioEntity entity = repository.getByUserName(userName);
		//UsuarioDtoResponse dto = addImageUrl(entity); //descomentei e vou subir para o heroku para testar se aparece a url
		UsuarioDtoResponse dto = mapper.toDto(entity);
		return dto;
	}

	/* antes de incluir a imagem
	 * public UsuarioDto create(UsuarioDto usuario) { UsuarioEntity usuarioNovo =
	 * mapper.toEntity(usuario);
	 * 
	 * PapelEntity papel = new PapelEntity(); papel =
	 * papelRepository.getByNome(usuario.getPapel()); usuarioNovo.setPapel(papel);
	 * 
	 * StatusEntity status = new StatusEntity(); status =
	 * statusRepository.getByNome(usuario.getStatus());
	 * usuarioNovo.setStatus(status);
	 * 
	 * EquipeEntity equipe = new EquipeEntity(); equipe =
	 * equipeRepository.getByNome(usuario.getEquipe());
	 * usuarioNovo.setEquipe(equipe);
	 * 
	 * return mapper.toDto(repository.save(usuarioNovo)); }
	 */
	
	
	public UsuarioDtoResponse update(UsuarioDtoRequest usuario) {
		UsuarioEntity usuarioHist = repository.getByUserName(usuario.getUserName());

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
			usuarioHist.setPassword(usuario.getPassword());
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
		
		if (usuario.getDtNascimento()!=null) {
			usuarioHist.setDtNascimento(usuario.getDtNascimento());
		}
		
			
		return mapper.toDto(repository.save(usuarioHist));

	}
	
	
	
	public void deleteByUserName(String userName) {
		UsuarioEntity usuario = getByUserName(userName);
		Long usuarioId = usuario.getId();

		repository.deleteById(usuarioId);
	}



	public UsuarioEntity createUsuario(UsuarioDtoRequest usuario) {
		UsuarioEntity usuarioNovo = mapper.toEntity(usuario);
	
		PapelEntity papel = new PapelEntity();
		papel = papelRepository.getByNome(usuario.getPapel());
		usuarioNovo.setPapel(papel);
		
		StatusEntity status = new StatusEntity();
		status = statusRepository.getByNome(usuario.getStatus());
		usuarioNovo.setStatus(status);
		
		EquipeEntity equipe = new EquipeEntity();
		equipe = equipeRepository.getByNome(usuario.getEquipe());
		usuarioNovo.setEquipe(equipe);
		
		usuarioNovo.setPassword(bCrypt.encode(usuarioNovo.getPassword()));
		return repository.save(usuarioNovo);
	
	
	}
	
	//retorna um DTO com a url da imagem
	public UsuarioDtoResponse addImageUrl(UsuarioEntity entity) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/usuario/{usuarioId}/image")
				.buildAndExpand(entity.getId()).toUri();
		System.out.println(uri);
		UsuarioDtoResponse usu = new UsuarioDtoResponse();
		usu = mapper.toDto(entity);
		String url = uri.toString();
		usu.setUrl(url);
		return usu;
	}
	
	
	//retorna um entity com url da imagem para fazer teste
	
	public UsuarioDtoResponse create(UsuarioDtoRequest dto, MultipartFile file) throws IOException {
		
		UsuarioEntity entitySaved = createUsuario(dto);
		imagemService.create(entitySaved, file);
		return addImageUrl(entitySaved);
	}

	
	public String resetSenha(String userName) throws MessagingException {
		
		UsuarioEntity entity = getByUserName(userName);
		String email,senhaNova,usuario;
		email = entity.getEmail();
		usuario= entity.getUserName();
		senhaNova = "ResetPac62021";
		entity.setPassword(bCrypt.encode(senhaNova));
		repository.save(entity);
		
		String subject = "Reset de senha";
		
		String body ="<tr><td>"+usuario+"</td><td></td>"+"<td>"+senhaNova+"</td><td></td><td></tr>";
		
		String msg =  "<table>" + "<thead style=color:blue>" + "<td><b>UserName</b></td><td></td>"
				  + "<td><b>Password</b></td><td></td>"+"</thead>" +
				  "<tbody>"+body +"</tbody>" + "</table>";
		
		return mailConfig.sendEmail(email, subject, msg);
		
		
	}
	
	//=========================================Tere 29/07/21=======================================
	 
	public UsuarioDtoResponse getByUserNameDto(String userName) {
		UsuarioEntity entity = getByUserName(userName);
		//Optional<UsuarioEntity> entityOptional = repository.findById(entity.getId());
		
		UsuarioDtoResponse usuarioDto = new UsuarioDtoResponse();
		usuarioDto.setNome(entity.getNome());
		usuarioDto.setNickName(entity.getNickName());
		
		PapelEntity papel = entity.getPapel();
		
		usuarioDto.setPapel(papel.getNome());
		//usuarioDto.setUserName(entity.getUserName());
		//usuarioDto.setEquipe(entity.getEquipe().getNome());
		//usuarioDto.setStatus(entity.getStatus().getNome());
		//usuarioDto.setEmail(entity.getEmail());
	
		
		
		/*
		 * URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(
		 * "/usuario/{usuarioId}/image") .buildAndExpand(entity.getId()).toUri();
		 * usuarioDto.setUrl(uri.toString());
		 */
		
		//UsuarioDtoResponse dto = mapper.toDto(entity);
		
		
		return usuarioDto;
	}
	
	
}




