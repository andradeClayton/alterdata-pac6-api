package org.serratec.java2backend.alterdatapac6.controller;

import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.PapelDto;
import org.serratec.java2backend.alterdatapac6.entity.PapelEntity;
import org.serratec.java2backend.alterdatapac6.service.PapelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/papel")
public class PapelController {
	
	@Autowired
	PapelService service;
	
	@GetMapping
	public List<PapelEntity> getAll(){
		return service.getAll();
	}

	@GetMapping("/{nome}")
	public PapelEntity getByNome(@PathVariable String nome) {
		return service.getByNome(nome);
	}
	
	@PostMapping
	public PapelDto create(@RequestBody PapelDto papel) {
		return service.create(papel);
	}
	
	@PutMapping
	public PapelDto update(@RequestBody PapelDto papel) {
		return service.update(papel);
	}
	
	@DeleteMapping("/{nome}")
	public void deleteByNome(@PathVariable String nome) {
		service.deleteByNome(nome);
	}
}
