package com.metabit.planilla.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/planilla")
public class BaseController {
	
	//@PreAuthorize("hasAuthority('EMPLEADO_DELETE')")
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	//@PreAuthorize("hasAuthority('PLANILLA_READ')")
	@GetMapping("/base")
	public String base() {
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "base";
	}
	
	//@PreAuthorize("hasAuthority('GENERO_WRITE')")
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
		return "redirect:/planilla/index";	
	}

}
