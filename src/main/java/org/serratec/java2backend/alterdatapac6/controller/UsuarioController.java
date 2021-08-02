package org.serratec.java2backend.alterdatapac6.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.serratec.java2backend.alterdatapac6.dto.UsuarioDtoRequest;
import org.serratec.java2backend.alterdatapac6.dto.UsuarioDtoResponse;
import org.serratec.java2backend.alterdatapac6.entity.ImagemEntity;
import org.serratec.java2backend.alterdatapac6.entity.UsuarioEntity;
import org.serratec.java2backend.alterdatapac6.exceptions.UsuarioNotFoundException;
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

import javassist.NotFoundException;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService service;
	
	@Autowired
	ImagemService imagemService;
	

	/*
	  antes de colocar a url da imagem no dto*/
	 
	 @GetMapping 
	 //public List<UsuarioEntity> getAll(){ return service.getAll(); }
	 public List<UsuarioDtoResponse> getAll() throws NotFoundException{
		 return service.getAllUser();
	 }
	 
	
	/*
	 * antes de incluir a url da imagem no getusername*/
	  
	@GetMapping("/{userName}") 
	  public ResponseEntity<UsuarioDtoResponse> getByUserNome(@PathVariable String userName) throws NotFoundException {
		
			return ResponseEntity.ok(service.getByUserName(userName));
		
	  }
	 
		/*
		 * // 01/08/21 troquei esse metodo pelo editaPerfil
		 * 
		 * @PutMapping public UsuarioDtoResponse update(@RequestBody UsuarioDtoRequest
		 * usuario) { return service.update(usuario); }
		 */
	
	@PutMapping("/editaPerfil")
	public UsuarioDtoResponse editaPerfilN1 (@RequestParam MultipartFile file , @RequestPart UsuarioDtoRequest usuario) throws IOException, NotFoundException {
		return service.editaPerfilN1(usuario, file);
	}
	
	@DeleteMapping("/{userName}")
	public String deleteByUserName(@PathVariable String userName) {
		String response = service.deleteByUserName(userName);
		
		if(response.equals(userName)) {
			return "Usuário "+userName+" deletado com sucesso!";
		}else {
			return "Usuário não cadastrado!";
		}
		
	}

	

	@PostMapping("/create")
	public UsuarioDtoResponse create(@RequestParam MultipartFile file , @RequestPart UsuarioDtoRequest usuario) throws IOException, NotFoundException {
		return service.create(usuario,file);
	}
	
		
	/*
	 * @PostMapping("/criaPerfil") public String criaPerfil(@RequestBody
	 * UsuarioDtoRequest usuario) throws NotFoundException, MessagingException {
	 * return service.criaPerfil(usuario);
	 * 
	 * }
	 */
	
	
	@GetMapping("{usuarioId}/image")
	public ResponseEntity<byte[]> getImage(@PathVariable Long usuarioId){
		ImagemEntity imagem = imagemService.getImagem(usuarioId);
		HttpHeaders header = new HttpHeaders();
		header.add("content-length", String.valueOf(imagem.getData().length));
		header.add("content-type", imagem.getMimeType());
		return new ResponseEntity<byte[]>(imagem.getData(),header, HttpStatus.OK);
	}
	
		
	@PutMapping("/resetSenha/{userName}")
	public String resetSenha(@PathVariable String userName) throws MessagingException {
		return service.resetSenha(userName);
	}
	
	//===========================================29/07/21======================================================
	
	@GetMapping("/dto/{userName}")
	public UsuarioDtoResponse getByUserNameDto(@PathVariable String userName) {
		return service.getByUserNameDto(userName);
	}
	
	
}
