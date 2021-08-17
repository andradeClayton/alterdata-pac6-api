package org.serratec.java2backend.alterdatapac6.repository;

import org.serratec.java2backend.alterdatapac6.entity.ImagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<ImagemEntity, Long> {

	ImagemEntity findByUsuarioId(Long id);

	/*
	 * @Query
	 * ("INSERT INTO ImagemEntity (data, mimeType, nome) VALUES 34277, 'image/jpeg','imagem'"
	 * ) ImagemEntity criaImagemPadrao ();
	 */

}
