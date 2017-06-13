package com.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.app.model.User;
import com.app.service.UserService;
import com.app.utils.Info;

import groovy.lang.Binding;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("userRegisterValidator")
	private Validator validator;
	
	@InitBinder
	private void InitBinder(WebDataBinder webDataBinder){
		webDataBinder.setValidator(validator);
	}
	
	@RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
	public ModelAndView showLoginForm() {
		ModelAndView modelAndView = new ModelAndView();
		User u = new User();
		modelAndView.addObject("user", u);
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping("/register")
	public ModelAndView showRegisterForm() {
		ModelAndView modelAndView = new ModelAndView();
		User u = new User();
		modelAndView.addObject("user", u);
		modelAndView.setViewName("register");
		return modelAndView;
	}

	@PostMapping("/register")
	public ModelAndView register(@ModelAttribute @Valid  User u, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			mv.setViewName("/register");
		} else {
			userService.saveUser(u);
			mv.setViewName("redirect:/login");
		}
		return mv;
	}
}
