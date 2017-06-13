package com.app.controller.user;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.User;
import com.app.service.UserService;
import com.app.utils.Info;

@Controller
public class ProfileController {
	@Autowired
	UserService userService;

	@Autowired
	@Qualifier("userEditValidator")
	private Validator validator;

	@InitBinder
	private void InitBinder(WebDataBinder webDataBinder) {
		webDataBinder.setValidator(validator);
	}

	@GetMapping("/user/profile")
	public ModelAndView showEditForm() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		User currStudent = userService.findByLogin(login);

		Integer countCompletedTasks = userService.getCountCompletedTasksByUser(currStudent);
		Double avarageMark = userService.getAvarageMarkByUser(currStudent);

		ModelAndView mv = new ModelAndView("user_profile");

		mv.addObject("currStudent", currStudent);
		mv.addObject("count", countCompletedTasks);
		mv.addObject("mark", avarageMark);

		return mv;
	}

	@PostMapping(value = "/user/edit")
	public ModelAndView editUserProfile(@ModelAttribute @Valid User currStudent, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("redirect:/user/profile");
		
		if(bindingResult.hasErrors()){
			// error logic
		} else {
		userService.updateUser(currStudent);
			mv.addObject("currStudent", currStudent);
		}
		return mv;
	}

	@PostMapping("/user/upload/{id}")
	public ModelAndView uploadUserProfilePhoto(@PathVariable Integer id, @RequestParam MultipartFile uploadfile) {

		User currStudent = userService.findOne(id);

		userService.saveUserProfileImage(currStudent, uploadfile);
		userService.updateUser(currStudent);


		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("redirect:/user/profile");
		return modelAndView;
	}

	@GetMapping("/user/materials")
	public ModelAndView showMaterialsForUser() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user_materials");

		return modelAndView;
	}

}
