package org.serratec.java2backend.alterdatapac6.service;

import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.EquipeDto;
import org.serratec.java2backend.alterdatapac6.entity.EquipeEntity;
import org.serratec.java2backend.alterdatapac6.mapper.EquipeMapper;
import org.serratec.java2backend.alterdatapac6.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipeService {
	
	@Autowired
	EquipeRepository repository;

	@Autowired
	EquipeMapper mapper;

	public List<EquipeEntity> getAll() {

		return repository.findAll();
	}

	public EquipeEntity getByNome(String nome) {
		return repository.getByNome(nome);
	}

	public EquipeDto create(EquipeDto equipe) {
		EquipeEntity equipeNova = mapper.toEntity(equipe);
		return mapper.toDto(repository.save(equipeNova));
	}

	public EquipeDto update(EquipeDto equipe) {
		EquipeEntity equipeNova = mapper.toEntity(equipe);
		EquipeEntity equipeHist = repository.getByNome(equipeNova.getNome());

		if (equipeNova.getDescricao() != null) {
			equipeHist.setDescricao(equipeNova.getDescricao());
		}
		return mapper.toDto(repository.save(equipeHist));

	}

	public void deleteByNome(String nome) {
		EquipeEntity equipe = getByNome(nome);
		Long equipeId = equipe.getId();

		repository.deleteById(equipeId);
	}


}
