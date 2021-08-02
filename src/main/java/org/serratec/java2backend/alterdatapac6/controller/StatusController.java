package org.serratec.java2backend.alterdatapac6.controller;

import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.StatusDto;
import org.serratec.java2backend.alterdatapac6.entity.StatusEntity;
import org.serratec.java2backend.alterdatapac6.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

@RestController
@RequestMapping("/status")
public class StatusController {

	@Autowired
	StatusService service;

	@GetMapping
	public List<StatusEntity> getAll() throws NotFoundException {
		return service.getAll();
	}

	@GetMapping("/{nome}")
	public StatusEntity getById(@PathVariable("nome") String nome) throws NotFoundException {
		return service.getByNome(nome);
	}

	@PostMapping
	public StatusDto create(@RequestBody StatusDto Categoria) {
		return service.create(Categoria);
	}

	@PutMapping("/{nomeStatus}")
	public StatusDto update(@PathVariable String nomeStatus, @RequestBody StatusDto status)
			throws NotFoundException {
		return service.update(nomeStatus,status);
	}
	
	/*
	 * @PutMapping() public StatusDto update(@RequestBody StatusDto status) throws
	 * NotFoundException { return service.update(status); }
	 */

	@DeleteMapping("/{nome}")
	public void deleteByNome(@PathVariable String nome) {
		service.deleteByNome(nome);
	}

	

}
