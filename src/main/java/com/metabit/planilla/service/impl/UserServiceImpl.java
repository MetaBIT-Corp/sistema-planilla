package com.metabit.planilla.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.jdbc.Expectations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.RolesRecursosPrivilegios;
import com.metabit.planilla.repository.UserJpaRepository;

@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService{

	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.metabit.planilla.entity.Usuario user = userJpaRepository.findByUsername(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		if(user != null) {
			user.setIntentos(user.getIntentos()+1);
			
			if(user.getIntentos()>3) {
				user.setEnabled(false);
			}
			userJpaRepository.save(user);
		}
		
		return buildUser(user, authorities);
 	}
	
	private User buildUser(com.metabit.planilla.entity.Usuario user, List<GrantedAuthority> authorities) {
		 
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), 
				true, true, true,  authorities);
	}
	
	private List<GrantedAuthority> buildAuthorities(List<Rol> roles){
		List<GrantedAuthority>  auths = new ArrayList<GrantedAuthority>();
		
		for(Rol r:roles) {
			List<RolesRecursosPrivilegios> rolesRecursosPrivilegios = r.getRolesRecursosPrivilegios();
			String authority="";
			for(RolesRecursosPrivilegios rrp:rolesRecursosPrivilegios) {
				authority=rrp.getRecurso().getRecurso()+"_"+rrp.getPrivilegio().getPrivilegio();
				auths.add(new SimpleGrantedAuthority(authority));
			}
		}
		
		return new ArrayList<GrantedAuthority>(auths);
	}
	

}
