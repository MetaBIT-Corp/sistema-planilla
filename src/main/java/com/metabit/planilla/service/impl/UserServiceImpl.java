package com.metabit.planilla.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.UserRole;
import com.metabit.planilla.repository.UserJpaRepository;

@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService{

	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.metabit.planilla.entity.User user = userJpaRepository.findByUsername(username);
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserRole());
		return buildUser(user, authorities);
 	}
	
	private User buildUser(com.metabit.planilla.entity.User user, List<GrantedAuthority> authorities) {
		 
		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), 
				true, true, true,  authorities);
	}
	
	private List<GrantedAuthority> buildAuthorities(Set<UserRole> userRoles){
		Set<GrantedAuthority>  auths = new HashSet<GrantedAuthority>();
		
		for(UserRole userRole : userRoles) {
			auths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		
		return new ArrayList<GrantedAuthority>(auths);
	}
	

}
