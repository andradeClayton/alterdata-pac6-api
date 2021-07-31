package org.serratec.java2backend.alterdatapac6.service;

import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.StatusDto;
import org.serratec.java2backend.alterdatapac6.entity.StatusEntity;
import org.serratec.java2backend.alterdatapac6.mapper.StatusMapper;
import org.serratec.java2backend.alterdatapac6.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

	@Autowired
	StatusRepository repository;

	@Autowired
	StatusMapper mapper;

	public List<StatusEntity> getAll() {

		return repository.findAll();
	}

	public StatusEntity getByNome(String nome) {
		return repository.getByNome(nome);
	}

	public StatusDto create(StatusDto status) {
		StatusEntity statusNovo = mapper.toEntity(status);
		return mapper.toDto(repository.save(statusNovo));
	}

	public StatusDto update(StatusDto status) {
		StatusEntity statusNovo = mapper.toEntity(status);
		StatusEntity statusHist = repository.getByNome(statusNovo.getNome());

		/*
		 * if (statusNovo.getDescricao() != null) {
		 * statusHist.setDescricao(statusNovo.getDescricao()); }
		 */
		if(statusNovo.getEmoji()!=null) {
			statusHist.setEmoji(statusNovo.getEmoji());
		}
		return mapper.toDto(repository.save(statusHist));

	}

	public void deleteByNome(String nome) {
		StatusEntity status = getByNome(nome);
		Long statusID = status.getId();

		repository.deleteById(statusID);
	}

}
