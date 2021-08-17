package org.serratec.java2backend.alterdatapac6.service;

import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.EquipeDto;
import org.serratec.java2backend.alterdatapac6.entity.EquipeEntity;
import org.serratec.java2backend.alterdatapac6.exceptions.EquipeDuplicadaException;
import org.serratec.java2backend.alterdatapac6.exceptions.EquipeNotFoundException;
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

	public EquipeEntity getByNome(String nome) throws EquipeNotFoundException {
		
		EquipeEntity equipe= repository.getByNome(nome);
		
		if(equipe==null) {
			throw new EquipeNotFoundException("Equipe "+nome+" não encrontrada!");
		}
		
		return equipe;
	}

	public EquipeDto create(EquipeDto equipe) throws EquipeDuplicadaException {
		EquipeEntity equipeBd = repository.getByNome(equipe.getNome());

		if (equipeBd != null) {
			throw new EquipeDuplicadaException("A equipe " + equipe.getNome() + " já exite! Escolha outro nome.");
		}

		EquipeEntity equipeNova = mapper.toEntity(equipe);
		return mapper.toDto(repository.save(equipeNova));
	}

	public EquipeDto update(String nomeEquipe, EquipeDto equipe) throws EquipeDuplicadaException {
		EquipeEntity equipeNova = mapper.toEntity(equipe);
		EquipeEntity equipeHist = repository.getByNome(nomeEquipe);

		if (equipeNova.getNome() != null) {

			EquipeEntity equipeBd = repository.getByNome(equipe.getNome());

			if (equipeBd != null) {
				throw new EquipeDuplicadaException("A equipe " + equipe.getNome() + " já exite! Escolha outro nome.");
			}
			equipeHist.setNome(equipeNova.getNome());
		}

		if (equipeNova.getDescricao() != null) {
			equipeHist.setDescricao(equipeNova.getDescricao());
		}
		return mapper.toDto(repository.save(equipeHist));

	}

	/*
	 * public EquipeDto update(EquipeDto equipe) { EquipeEntity equipeNova =
	 * mapper.toEntity(equipe); EquipeEntity equipeHist =
	 * repository.getByNome(equipeNova.getNome());
	 * 
	 * if(equipeNova.getNome()!=null) { equipeHist.setNome(equipeNova.getNome()); }
	 * 
	 * if (equipeNova.getDescricao() != null) {
	 * equipeHist.setDescricao(equipeNova.getDescricao()); } return
	 * mapper.toDto(repository.save(equipeHist));
	 * 
	 * }
	 */

	public void deleteByNome(String nome) throws EquipeNotFoundException {
		EquipeEntity equipe = getByNome(nome);
		Long equipeId = equipe.getId();
		
		if(equipe==null) {
			throw new EquipeNotFoundException("Equipe "+nome+" não encrontrada!");
		}
		repository.deleteById(equipeId);
	}

}
