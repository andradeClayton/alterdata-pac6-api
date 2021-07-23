package org.serratec.java2backend.alterdatapac6.controller;

import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.PapelDto;
import org.serratec.java2backend.alterdatapac6.dto.UsuarioDto;
import org.serratec.java2backend.alterdatapac6.entity.PapelEntity;
import org.serratec.java2backend.alterdatapac6.entity.UsuarioEntity;
import org.serratec.java2backend.alterdatapac6.service.UsuarioService;
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
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService service;
	

	@GetMapping
	public List<UsuarioEntity> getAll(){
		return service.getAll();
	}

	@GetMapping("/{nome}")
	public UsuarioEntity getByUserNome(@PathVariable String nome) {
		return service.getByUserName(nome);
	}
	
	@PostMapping
	public UsuarioDto create(@RequestBody UsuarioDto usuario) {
		return service.create(usuario);
	}
	
	@PutMapping
	public UsuarioDto update(@RequestBody UsuarioDto usuario) {
		return service.update(usuario);
	}
	
	@DeleteMapping("/{nome}")
	public void deleteByUserName(@PathVariable String nome) {
		service.deleteByUserName(nome);
	}

	
}
