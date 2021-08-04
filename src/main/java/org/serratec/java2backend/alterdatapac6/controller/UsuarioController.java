package org.serratec.java2backend.alterdatapac6.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.ws.rs.core.MediaType;

import org.serratec.java2backend.alterdatapac6.dto.UsuarioDtoRequest;
import org.serratec.java2backend.alterdatapac6.dto.UsuarioDtoResponse;
import org.serratec.java2backend.alterdatapac6.entity.ImagemEntity;
import org.serratec.java2backend.alterdatapac6.exceptions.UsuarioDuplicadoException;
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
	

	/*
	  antes de colocar a url da imagem no dto*/
	 
	 @GetMapping 
	 //public List<UsuarioEntity> getAll(){ return service.getAll(); }
	 public List<UsuarioDtoResponse> getAll() throws UsuarioNotFoundException{
		 return service.getAllUser();
	 }
	 
	
	/*
	 * antes de incluir a url da imagem no getusername*/
	  
	@GetMapping("/{userName}") 
	  public ResponseEntity<UsuarioDtoResponse> getByUserNome(@PathVariable String userName) throws UsuarioNotFoundException {
		
			return ResponseEntity.ok(service.getByUserName(userName));
		
	  }
	 
		/*
		 * // 01/08/21 troquei esse metodo pelo editaPerfil
		 * 
		 * @PutMapping public UsuarioDtoResponse update(@RequestBody UsuarioDtoRequest
		 * usuario) { return service.update(usuario); }
		 */
	
	@PutMapping(value="/editaPerfilN1/{userName}", consumes = {MediaType.MULTIPART_FORM_DATA})
	//@PutMapping("/editaPerfilN1/{userName}")
	public UsuarioDtoResponse editaPerfilN1 (@PathVariable String userName, @RequestPart MultipartFile file , @RequestPart UsuarioDtoRequest usuario) throws IOException, UsuarioNotFoundException, UsuarioDuplicadoException {
		return service.editaPerfilN1(userName,usuario, file);
	}
	
	
	@PutMapping("/editaPerfilN2/{userName}")
	public UsuarioDtoResponse editaPerfilN2 (@PathVariable String userName,@RequestBody UsuarioDtoRequest usuario) throws IOException, UsuarioNotFoundException {
		return service.editaPerfilN2(userName,usuario);
	}
	
	@PutMapping("/editaPerfilN3/{userName}")
	public UsuarioDtoResponse editaPerfilN3 (@PathVariable String userName,@RequestBody UsuarioDtoRequest usuario) throws IOException, UsuarioNotFoundException {
		return service.editaPerfilN3(userName,usuario);
	}
	
	
	@DeleteMapping("/{userName}")
	public String deleteByUserName(@PathVariable String userName) throws UsuarioNotFoundException {
		String response = service.deleteByUserName(userName);
		
		if(response.equals(userName)) {
			return "Usuário "+userName+" deletado com sucesso!";
		}else {
			return "Usuário não cadastrado!";
		}
		
	}

	

	/*
	 * @PostMapping("/create") public UsuarioDtoResponse create(@RequestParam
	 * MultipartFile file , @RequestPart UsuarioDtoRequest usuario) throws
	 * IOException, NotFoundException, UsuarioNotFoundException { return
	 * service.create(usuario,file); }
	 */
	
		
	
	  @PostMapping("/criaPerfil/{userName}") 
	  public String criaPerfil(@PathVariable String userName, @RequestBody UsuarioDtoRequest usuario) throws MessagingException, UsuarioNotFoundException {
	  return service.criaPerfil(userName,usuario);
	  
	  }
	 
	
	
	@GetMapping("{usuarioId}/image")
	public ResponseEntity<byte[]> getImage(@PathVariable Long usuarioId){
		ImagemEntity imagem = imagemService.getImagem(usuarioId);
		HttpHeaders header = new HttpHeaders();
		header.add("content-length", String.valueOf(imagem.getData().length));
		header.add("content-type", imagem.getMimeType());
		return new ResponseEntity<byte[]>(imagem.getData(),header, HttpStatus.OK);
	}
	
		
	@PutMapping("/resetSenha/{userName}")
	public String resetSenha(@PathVariable String userName) throws MessagingException, UsuarioNotFoundException {
		return service.resetSenha(userName);
	}
	
	//===========================================29/07/21======================================================
	
	@GetMapping("/dto/{userName}")
	public UsuarioDtoResponse getByUserNameDto(@PathVariable String userName) {
		return service.getByUserNameDto(userName);
	}
	
	
}
