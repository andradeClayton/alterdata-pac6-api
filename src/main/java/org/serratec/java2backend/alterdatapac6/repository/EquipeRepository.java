package org.serratec.java2backend.alterdatapac6.repository;

import org.serratec.java2backend.alterdatapac6.entity.EquipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<EquipeEntity, Long>{

	EquipeEntity getByNome(String nome);

}
