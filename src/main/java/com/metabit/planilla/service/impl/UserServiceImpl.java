package com.metabit.planilla.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.RolesRecursosPrivilegios;
import com.metabit.planilla.entity.Usuario;
import com.metabit.planilla.repository.UserJpaRepository;

@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService{

	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.metabit.planilla.entity.Usuario user = userJpaRepository.findByUsername(username);
		List<GrantedAuthority> authorities = buildAuthorities(user.getRoles());
		userAttemps(user);
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
	
	private void userAttemps(Usuario user) {
		if(user != null) {
			user.setIntentos(user.getIntentos()+1);
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession(true);
			session.setAttribute("user_attemps",user.getIntentos());
			
			if(user.getIntentos()>3) {
				user.setEnabled(false);
			}
			userJpaRepository.save(user);
		}
	}
	
}
