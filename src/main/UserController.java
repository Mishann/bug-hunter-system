package com.app.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.User;
import com.app.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/admin/user_profile")
	public ModelAndView showUsersProfile() {

		ModelAndView mv = new ModelAndView("admin_user_profile");
		return mv;
	}

	@GetMapping(value="/admin/all_users")
	public ModelAndView showAllUsers(@RequestParam(required=false) String surname){
		List<User> students = userService.findAllStudents();						
	
		// пошук за прізвищем
		if(surname != null)
			students = students
						.stream()
						.filter(st->st.getUserSurname().toLowerCase().contains(surname.toLowerCase()))
						.collect(Collectors.toList());
		
		//формуєм ліст із кількістю розвязаних завдань і оцінка для кожного користувача		
		List<Integer> tasksCountList = new ArrayList<>();
		List<Double> tasksAvarageMarkList = new ArrayList<>();
		
		for (User u : students) {
			tasksCountList.add(userService.getCountCompletedTasksByUser(u));
			tasksAvarageMarkList.add(userService.getAvarageMarkByUser(u));
		}
		
		ModelAndView mv = new ModelAndView("admin_all_users");
		mv.addObject("students", students);
		mv.addObject("amountList", tasksCountList);
		mv.addObject("markList", tasksAvarageMarkList );
		
		return mv;
	}
	
	@GetMapping(value="/admin/all_users/{id}")
	public ModelAndView showSelectedUser(@PathVariable Integer id){	
		
		User student = userService.findOne(id);						
	
		Integer countCompletedTasks = userService.getCountCompletedTasksByUser(student);
		Double	avarageMark = userService.getAvarageMarkByUser(student);
		
		//формуєм ліст із кількістю розвязаних завдань і оцінка для кожного користувача
		
		ModelAndView mv = new ModelAndView("admin_user_profile");
					mv.addObject("student",student);
					mv.addObject("mark",avarageMark);
					mv.addObject("count",countCompletedTasks);
					
		return mv;
	}
	
	
	
}
