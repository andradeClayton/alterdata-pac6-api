package org.serratec.java2backend.alterdatapac6.security;

import org.serratec.java2backend.alterdatapac6.entity.UsuarioEntity;
import org.serratec.java2backend.alterdatapac6.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService{

	@Autowired
	UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioEntity usuario = repository.findByUserName(username);
		if(usuario == null) {
			System.out.println("Usuario não existe");
			return null;
		}
		return new UserSS(usuario.getId(),usuario.getUserName(),usuario.getPassword());
	}

}