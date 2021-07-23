package org.serratec.java2backend.alterdatapac6.mapper;

import org.serratec.java2backend.alterdatapac6.dto.PapelDto;
import org.serratec.java2backend.alterdatapac6.entity.PapelEntity;
import org.springframework.stereotype.Component;

@Component
public class PapelMapper {
	
	public PapelEntity toEntity(PapelDto dto) {
		PapelEntity entity = new PapelEntity();
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		
		return entity;
	}
	
	public PapelDto toDto(PapelEntity entity) {
		PapelDto dto = new PapelDto();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
	
		return dto;
	}

}
