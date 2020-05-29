package com.metabit.planilla.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.metabit.planilla.entity.Usuario;
import com.metabit.planilla.repository.UserJpaRepository;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent>{
	
	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Override
    public void onApplicationEvent(AuthenticationSuccessEvent evt) {
		
        String username = evt.getAuthentication().getName();
		Usuario usuario  = userJpaRepository.findByUsername(username);
		usuario.setIntentos(0);
		userJpaRepository.save(usuario);
    } 
}
