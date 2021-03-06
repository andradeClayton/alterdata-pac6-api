package org.serratec.java2backend.alterdatapac6.service;

import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.PapelDto;
import org.serratec.java2backend.alterdatapac6.entity.PapelEntity;
import org.serratec.java2backend.alterdatapac6.exceptions.PapelDuplicadoException;
import org.serratec.java2backend.alterdatapac6.exceptions.PapelNotFoundException;
import org.serratec.java2backend.alterdatapac6.mapper.PapelMapper;
import org.serratec.java2backend.alterdatapac6.repository.PapelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PapelService {

	@Autowired
	PapelRepository repository;

	@Autowired
	PapelMapper mapper;

	public List<PapelEntity> getAll() {

		return repository.findAll();
	}

	public PapelEntity getByNome(String nome) throws PapelNotFoundException {
		
		PapelEntity papelBd = repository.getByNome(nome);
		
		if(papelBd==null) {
			throw new PapelNotFoundException("Papel "+nome+" não encontrado!");
		}
		
		return repository.getByNome(nome);
	}

	public PapelDto create(PapelDto papel) throws PapelDuplicadoException {
		PapelEntity papelBd = repository.getByNome(papel.getNome());
		if(papelBd!=null) {
			throw new PapelDuplicadoException("O papel "+papel.getNome()+" já existe! Escolha outro nome.");
		}
		
		PapelEntity papelNovo = mapper.toEntity(papel);
		return mapper.toDto(repository.save(papelNovo));
	}

	public PapelDto update(String nomePapel,PapelDto papel) throws PapelDuplicadoException, PapelNotFoundException {
		PapelEntity papelNovo = mapper.toEntity(papel);
		PapelEntity papelHist = getByNome(nomePapel);

		if (papelNovo.getNome() != null) {
			PapelEntity papelBd = repository.getByNome(papel.getNome());
			if(papelBd!=null) {
				throw new PapelDuplicadoException("O papel "+papel.getNome()+" já existe! Escolha outro nome.");
			}
			
			papelHist.setNome(papelNovo.getNome());
		}
		
		if (papelNovo.getDescricao() != null) {
			papelHist.setDescricao(papelNovo.getDescricao());
		}
		
		if(papelNovo.getCor()!=null) {
			papelHist.setCor(papelNovo.getCor());
		}
		return mapper.toDto(repository.save(papelHist));

	}
	
	
	/*
	 * public PapelDto update(PapelDto papel) { PapelEntity papelNovo =
	 * mapper.toEntity(papel); PapelEntity papelHist =
	 * repository.getByNome(papelNovo.getNome());
	 * 
	 * if (papelNovo.getNome() != null) { papelHist.setNome(papelNovo.getNome()); }
	 * 
	 * if (papelNovo.getDescricao() != null) {
	 * papelHist.setDescricao(papelNovo.getDescricao()); }
	 * 
	 * if(papelNovo.getCor()!=null) { papelHist.setCor(papelNovo.getCor()); } return
	 * mapper.toDto(repository.save(papelHist));
	 * 
	 * }
	 */
	public void deleteByNome(String nome) throws PapelNotFoundException {
		PapelEntity papel = getByNome(nome);
		Long papelId = papel.getId();

		repository.deleteById(papelId);
	}

}
