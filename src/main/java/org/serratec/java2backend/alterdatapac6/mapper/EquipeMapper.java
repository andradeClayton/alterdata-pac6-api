package org.serratec.java2backend.alterdatapac6.mapper;


import org.serratec.java2backend.alterdatapac6.dto.EquipeDto;
import org.serratec.java2backend.alterdatapac6.entity.EquipeEntity;
import org.springframework.stereotype.Component;

@Component
public class EquipeMapper {
	
	public EquipeEntity toEntity(EquipeDto dto) {
		EquipeEntity entity = new EquipeEntity();
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		
		return entity;
	}
	
	public EquipeDto toDto(EquipeEntity entity) {
		EquipeDto dto = new EquipeDto();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
	
		return dto;
	}

}
