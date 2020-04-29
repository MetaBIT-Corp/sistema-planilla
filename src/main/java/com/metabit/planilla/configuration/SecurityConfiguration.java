package com.metabit.planilla.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
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
			.antMatchers("/css/*","/imgs/*").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login/login-show").loginProcessingUrl("/planilla/login-check")
			.usernameParameter("username").passwordParameter("password")
			.defaultSuccessUrl("/login/loginsuccess").permitAll()
			.and()
			.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll();
	}
	
	
}
