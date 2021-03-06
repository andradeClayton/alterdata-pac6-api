package org.serratec.java2backend.alterdatapac6.util;

import org.serratec.java2backend.alterdatapac6.exceptions.EquipeDuplicadaException;
import org.serratec.java2backend.alterdatapac6.exceptions.EquipeNotFoundException;
import org.serratec.java2backend.alterdatapac6.exceptions.PapelDuplicadoException;
import org.serratec.java2backend.alterdatapac6.exceptions.PapelNotFoundException;
import org.serratec.java2backend.alterdatapac6.exceptions.StatusDuplicadoException;
import org.serratec.java2backend.alterdatapac6.exceptions.StatusNotFoundException;
import org.serratec.java2backend.alterdatapac6.exceptions.UsuarioDuplicadoException;
import org.serratec.java2backend.alterdatapac6.exceptions.UsuarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(UsuarioNotFoundException.class)
	public ResponseEntity<String> tratarUsuarioNotFoundException(UsuarioNotFoundException exception){
		return ResponseEntity.notFound()
				.header("x-error-msg", exception.getMessage())
				.build();
				
	}
	
	
	@ExceptionHandler(UsuarioDuplicadoException.class)
	public ResponseEntity<String> tratarSaldoInsuficienteException(UsuarioDuplicadoException exception) {
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.header("x-error-msg", exception.getMessage())
				.build();
	}

	
	@ExceptionHandler(EquipeNotFoundException.class)
	public ResponseEntity<String> tratarUsuarioNotFoundException(EquipeNotFoundException exception){
		return ResponseEntity.notFound()
				.header("x-error-msg", exception.getMessage())
				.build();
	}

	@ExceptionHandler(EquipeDuplicadaException.class)
	public ResponseEntity<String> tratarSaldoInsuficienteException(EquipeDuplicadaException exception) {
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.header("x-error-msg", exception.getMessage())
				.build();
	}

	@ExceptionHandler(PapelNotFoundException.class)
	public ResponseEntity<String> tratarUsuarioNotFoundException(PapelNotFoundException exception){
		return ResponseEntity.notFound()
				.header("x-error-msg", exception.getMessage())
				.build();
	}

	@ExceptionHandler(PapelDuplicadoException.class)
	public ResponseEntity<String> tratarSaldoInsuficienteException(PapelDuplicadoException exception) {
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.header("x-error-msg", exception.getMessage())
				.build();
	}
	

	@ExceptionHandler(StatusNotFoundException.class)
	public ResponseEntity<String> tratarUsuarioNotFoundException(StatusNotFoundException exception){
		return ResponseEntity.notFound()
				.header("x-error-msg", exception.getMessage())
				.build();
	}

	@ExceptionHandler(StatusDuplicadoException.class)
	public ResponseEntity<String> tratarSaldoInsuficienteException(StatusDuplicadoException exception) {
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.header("x-error-msg", exception.getMessage())
				.build();
	}
	
	
}

