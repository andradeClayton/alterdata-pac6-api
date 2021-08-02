package org.serratec.java2backend.alterdatapac6.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.serratec.java2backend.alterdatapac6.entity.ImagemEntity;
import org.serratec.java2backend.alterdatapac6.entity.UsuarioEntity;
import org.serratec.java2backend.alterdatapac6.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagemService {
	
	@Autowired
	ImagemRepository repository;
	
	
	@Transactional
	public ImagemEntity create(UsuarioEntity entity, MultipartFile file) throws IOException {
		ImagemEntity image = new ImagemEntity();
		image.setUsuario(entity);
		image.setData(file.getBytes());
		image.setMimeType(file.getContentType());
		image.setNome("Imagem");
		return repository.save(image);
	}
	
	@Transactional
	public ImagemEntity getImagem(Long id) {
		ImagemEntity imageUsuario = repository.findByUsuarioId(id);
		return imageUsuario;
	}
	

}
