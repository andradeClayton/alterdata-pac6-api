package org.serratec.java2backend.alterdatapac6.mapper;

import org.serratec.java2backend.alterdatapac6.dto.StatusDto;
import org.serratec.java2backend.alterdatapac6.entity.StatusEntity;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

	public StatusEntity toEntity(StatusDto dto) {
		StatusEntity entity = new StatusEntity();
		entity.setNome(dto.getNome());
<<<<<<< HEAD
	//	entity.setDescricao(dto.getDescricao());
=======
		entity.setDescricao(dto.getDescricao());
>>>>>>> master
		entity.setEmoji(dto.getEmoji());
		
		return entity;
	}
	
	public StatusDto toDto(StatusEntity entity) {
		StatusDto dto = new StatusDto();
		dto.setNome(entity.getNome());
<<<<<<< HEAD
	//	dto.setDescricao(entity.getDescricao());
=======
		dto.setDescricao(entity.getDescricao());
>>>>>>> master
		dto.setEmoji(entity.getEmoji());
	
		return dto;
	}
	
}
