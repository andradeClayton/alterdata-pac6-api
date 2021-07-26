package org.serratec.java2backend.alterdatapac6.repository;

import org.serratec.java2backend.alterdatapac6.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	UsuarioEntity getByNome(String nome);

	@Query("SELECT u FROM UsuarioEntity u WHERE u.userName = :userName")
	UsuarioEntity getByUserName(@Param("userName")String useName);

	UsuarioEntity findByUserName(String username);

}
