package org.serratec.java2backend.alterdatapac6.mapper;

import org.serratec.java2backend.alterdatapac6.dto.StatusDto;
import org.serratec.java2backend.alterdatapac6.entity.StatusEntity;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

	public StatusEntity toEntity(StatusDto dto) {
		StatusEntity entity = new StatusEntity();
		entity.setNome(dto.getNome());
	//	entity.setDescricao(dto.getDescricao());
		entity.setEmoji(dto.getEmoji());
		
		return entity;
	}
	
	public StatusDto toDto(StatusEntity entity) {
		StatusDto dto = new StatusDto();
		dto.setNome(entity.getNome());
	//	dto.setDescricao(entity.getDescricao());
		dto.setEmoji(entity.getEmoji());
	
		return dto;
	}
	
}
