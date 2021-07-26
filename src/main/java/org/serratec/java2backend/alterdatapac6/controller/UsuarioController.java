package org.serratec.java2backend.alterdatapac6.controller;

import java.io.IOException;
import java.util.List;

import org.serratec.java2backend.alterdatapac6.dto.UsuarioDto;
import org.serratec.java2backend.alterdatapac6.entity.ImagemEntity;
import org.serratec.java2backend.alterdatapac6.entity.UsuarioEntity;
import org.serratec.java2backend.alterdatapac6.service.ImagemService;
import org.serratec.java2backend.alterdatapac6.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService service;
	
	@Autowired
	ImagemService imagemService;
	

	@GetMapping
	public List<UsuarioEntity> getAll(){
		return service.getAll();
	}

	@GetMapping("/{userName}")
	public UsuarioEntity getByUserNome(@PathVariable String userName) {
		return service.getByUserName(userName);
	}
	
	/* antes da inclusão de imagens
	 * @PostMapping public UsuarioDto create(@RequestBody UsuarioDto usuario) throws
	 * IOException { return service.create(usuario); }
	 */
	
	@PutMapping
	public UsuarioDto update(@RequestBody UsuarioDto usuario) {
		return service.update(usuario);
	}
	
	@DeleteMapping("/{nome}")
	public void deleteByUserName(@PathVariable String nome) {
		service.deleteByUserName(nome);
	}

	

	@PostMapping("/create")
	public UsuarioDto create(@RequestParam MultipartFile file , @RequestPart UsuarioDto usuario) throws IOException {
		return service.create(usuario,file);
	}
	
	
	/* 
	 * @PostMapping("/create") public ClientDTO create(@RequestParam MultipartFile
	 * file , @RequestPart ClientEntity entity) throws IOException { return
	 * service.create(entity, file); }
	 */
	
	
	@GetMapping("{usuarioId}/image")
	public ResponseEntity<byte[]> getImage(@PathVariable Long usuarioId){
		ImagemEntity imagem = imagemService.getImagem(usuarioId);
		HttpHeaders header = new HttpHeaders();
		header.add("content-length", String.valueOf(imagem.getData().length));
		header.add("content-type", imagem.getMimeType());
		return new ResponseEntity<byte[]>(imagem.getData(),header, HttpStatus.OK);
	}
	
	
	
	/*
	 * @GetMapping("/client/{clientId}/image") public ResponseEntity<byte[]>
	 * getImage(@PathVariable Long clientId){ ImageEntity imagem =
	 * imageService.getImagem(clientId); HttpHeaders header = new HttpHeaders();
	 * header.add("content-length", String.valueOf(imagem.getData().length));
	 * header.add("content-type", imagem.getMimeType()); return new
	 * ResponseEntity<byte[]>(imagem.getData(),header, HttpStatus.OK); }
	 */
	
	
}
