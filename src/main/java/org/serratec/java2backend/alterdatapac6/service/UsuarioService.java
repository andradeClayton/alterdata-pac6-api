package org.serratec.java2backend.alterdatapac6.service;

import java.io.Console;
import java.util.Date;
import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.PapelDto;
import org.serratec.java2backend.alterdatapac6.dto.UsuarioDto;
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
import org.springframework.stereotype.Service;

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
	
	public List<UsuarioEntity> getAll() {

		return repository.findAll();
	}

	public UsuarioEntity getByUserName(String useName) {
		return repository.getByUserName(useName);
	}

	public UsuarioDto create(UsuarioDto usuario) {
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
		
		return mapper.toDto(repository.save(usuarioNovo));
	}

	
	public UsuarioDto update(UsuarioDto usuario) {
		/*UsuarioEntity usuarioNovo = mapper.toEntity(usuario);*/
		UsuarioEntity usuarioHist = repository.getByUserName(usuario.getUseName());
		
		/*
		 * PapelEntity papel = new PapelEntity(); papel =
		 * papelRepository.getByNome(usuario.getPapel());
		 */
		
		/*
		 * StatusEntity status = new StatusEntity(); status =
		 * statusRepository.getByNome(usuario.getStatus());
		 */
		/*
		 * EquipeEntity equipe = new EquipeEntity(); equipe =
		 * equipeRepository.getByNome(usuario.getEquipe());
		 */
		
		

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
		
		if (usuario.getUseName() != null) {
			usuarioHist.setUseName(usuario.getUseName());
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


}
