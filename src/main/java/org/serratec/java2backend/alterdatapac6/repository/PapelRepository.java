package org.serratec.java2backend.alterdatapac6.repository;

import org.serratec.java2backend.alterdatapac6.entity.PapelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PapelRepository extends JpaRepository<PapelEntity,Long> {

	PapelEntity getByNome(String nome);

	

}
