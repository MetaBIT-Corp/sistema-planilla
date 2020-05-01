package com.metabit.planilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.planilla.entity.Usuario;
import com.metabit.planilla.repository.UserJpaRepository;

@Controller
@RequestMapping({"/planilla","/"})
public class BaseController {
	
	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/base")
	public String base() {
		return "base";
	}
	
	@GetMapping("/ejemplo")
	public String ejemplo() {
		return "example";
	}
	
	@GetMapping("/login")
	public ModelAndView showLoginForm(
			@RequestParam(name="error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		
		ModelAndView modelAndView = new ModelAndView("/login");
		modelAndView.addObject("error", error);
		modelAndView.addObject("logout", logout);

		return modelAndView;
	}
	
	@GetMapping({"/loginsuccess","/"})
	public String loginCheck() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario usuario  = userJpaRepository.findByUsername(user.getUsername());
		usuario.setIntentos(0);
		userJpaRepository.save(usuario);
		
		return "redirect:/planilla/index";	
	}

}
