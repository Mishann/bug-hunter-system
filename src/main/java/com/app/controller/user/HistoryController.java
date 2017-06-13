package com.app.controller.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.Answer;
import com.app.model.Task;
import com.app.model.Userhistory;
import com.app.service.TaskService;
import com.app.service.UserService;
import com.app.service.UserhistoryService;
import com.app.utils.Info;
import com.app.utils.TaskHelper;

@Controller
public class HistoryController {
	@Autowired
	UserService userService;

	@Autowired
	TaskService taskService;
	
	@Autowired
	UserhistoryService userHistoryService;

	@GetMapping("/user/history")
	public ModelAndView showhistoryPage() {
		ModelAndView mv = new ModelAndView("user_history_tab");

		List<Userhistory> unchecked = userHistoryService.getUserHistoryWithUnchekedTaskByUser(Info.currentUser);
		List<Userhistory> checked =   userHistoryService.getUserHistoryWithChekedTaskByUser(Info.currentUser);
		
		mv.addObject("unchecked", unchecked);
		mv.addObject("checked", checked);
		
		return mv;
		
	}
	
	@GetMapping("/user/history/{id}")
	public ModelAndView showHistoryTask(@PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("user_history_task");		
		
		Userhistory userHistory = userHistoryService.findOne(id);
		Task currTask = userHistory.getTask();
		Answer currAnswer = currTask.getAnswer();
		
		// якщо запитання закритого типу
				if (currTask.getTaskType().equals("Закрите")) {
					List<String> variants = TaskHelper.parseAndShuffle(currAnswer.getVariants(), "#");

					mv.addObject("variant1", variants.get(0));
					mv.addObject("variant2", variants.get(1));
					mv.addObject("variant3", variants.get(2));
					mv.addObject("variant4", variants.get(3));
				} 
		
		mv.addObject("history",userHistory);
				
		return mv;
	}
	
}
