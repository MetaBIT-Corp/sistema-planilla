package com.metabit.planilla.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("userServiceImpl")
	private UserDetailsService userServiceImpl;
	
	@Autowired
	public void configureGloal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers( //***************Agregar urls que no neciten logueo****************//
					"/planilla/ejemplo",
					"/planilla/puesto/***",
					"/planilla/base",
					"/planilla/index",
					"/planilla/empleado/**"
					).permitAll()
			.antMatchers("/css/**","/imgs/**","/js/**","/dist/**","/plugins/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/planilla/login").loginProcessingUrl("/planilla/login-check")
			.usernameParameter("username").passwordParameter("password")
			.defaultSuccessUrl("/planilla/loginsuccess").permitAll()
			.and()
			.logout().logoutUrl("/planilla/logout").logoutSuccessUrl("/planilla/login?logout").permitAll();
	}
	
	
}
