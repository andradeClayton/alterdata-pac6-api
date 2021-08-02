package org.serratec.java2backend.alterdatapac6.mapper;

import java.util.Date;

import org.serratec.java2backend.alterdatapac6.dto.PapelDto;
import org.serratec.java2backend.alterdatapac6.dto.UsuarioDtoRequest;
import org.serratec.java2backend.alterdatapac6.dto.UsuarioDtoResponse;
import org.serratec.java2backend.alterdatapac6.entity.PapelEntity;
import org.serratec.java2backend.alterdatapac6.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
	
	public UsuarioEntity toEntity(UsuarioDtoRequest dto) {
		UsuarioEntity entity = new UsuarioEntity();
		entity.setNome(dto.getNome());
		entity.setNickName(dto.getNickName());
		entity.setUserName(dto.getUserName());
		entity.setPassword(dto.getPassword());
		entity.setEmail(dto.getEmail());
		entity.setDtNascimento(dto.getDtNascimento());
		
		return entity;
	}
	
	public UsuarioDtoResponse toDto(UsuarioEntity entity) {
		UsuarioDtoResponse dto = new UsuarioDtoResponse();
		
		dto.setNome(entity.getNome());
		dto.setNickName(entity.getNickName());
		dto.setUserName(entity.getUserName());
		dto.setEmail(entity.getEmail());
		dto.setPapel(entity.getPapel().getNome());
		dto.setEquipe(entity.getEquipe().getNome());
		dto.setStatus(entity.getStatus().getNome());
		
		
		return dto;
	}

}
