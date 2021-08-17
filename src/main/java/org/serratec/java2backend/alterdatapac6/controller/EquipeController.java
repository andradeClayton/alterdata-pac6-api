package org.serratec.java2backend.alterdatapac6.controller;

import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.EquipeDto;
import org.serratec.java2backend.alterdatapac6.entity.EquipeEntity;
import org.serratec.java2backend.alterdatapac6.exceptions.EquipeDuplicadaException;
import org.serratec.java2backend.alterdatapac6.exceptions.EquipeNotFoundException;
import org.serratec.java2backend.alterdatapac6.service.EquipeService;
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
@RequestMapping("/equipe")
public class EquipeController {
	
	
	@Autowired
	EquipeService service;
	
	@GetMapping
	public List<EquipeEntity> getAll(){
		return service.getAll();
	}

	@GetMapping("/{nome}")
	public EquipeEntity getByNome(@PathVariable String nome) throws EquipeNotFoundException {
		return service.getByNome(nome);
	}
	
	@PostMapping
	public EquipeDto create(@RequestBody EquipeDto papel) throws EquipeDuplicadaException {
		return service.create(papel);
	}
	
	@PutMapping("/{nomeEquipe}")
	public EquipeDto update(@PathVariable String nomeEquipe, @RequestBody EquipeDto equipe) throws EquipeDuplicadaException {
		return service.update(nomeEquipe,equipe);
	}
	
	
	/*
	 * @PutMapping public EquipeDto update(@RequestBody EquipeDto papel) { return
	 * service.update(papel); }
	 */
	
	@DeleteMapping("/{nome}")
	public void deleteByNome(@PathVariable String nome) throws EquipeNotFoundException {
		service.deleteByNome(nome);
	}

}
