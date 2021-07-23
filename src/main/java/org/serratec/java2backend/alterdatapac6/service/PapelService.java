package org.serratec.java2backend.alterdatapac6.service;

import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.PapelDto;
import org.serratec.java2backend.alterdatapac6.entity.PapelEntity;
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

	public PapelEntity getByNome(String nome) {
		return repository.getByNome(nome);
	}

	public PapelDto create(PapelDto papel) {
		PapelEntity papelNovo = mapper.toEntity(papel);
		return mapper.toDto(repository.save(papelNovo));
	}

	public PapelDto update(PapelDto papel) {
		PapelEntity papelNovo = mapper.toEntity(papel);
		PapelEntity papelHist = repository.getByNome(papelNovo.getNome());

		if (papelNovo.getDescricao() != null) {
			papelHist.setDescricao(papelNovo.getDescricao());
		}
		return mapper.toDto(repository.save(papelHist));

	}

	public void deleteByNome(String nome) {
		PapelEntity papel = getByNome(nome);
		Long papelId = papel.getId();

		repository.deleteById(papelId);
	}

}
