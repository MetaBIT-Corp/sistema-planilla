package com.metabit.planilla.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class BaseController {
	
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
		
		return "redirect:/index";
	}

}
