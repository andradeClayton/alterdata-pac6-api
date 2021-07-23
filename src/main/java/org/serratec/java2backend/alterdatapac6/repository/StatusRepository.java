package org.serratec.java2backend.alterdatapac6.repository;

import org.serratec.java2backend.alterdatapac6.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

	StatusEntity getByNome(String nome);

}
