package com.app.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.Userhistory;
import com.app.service.UserhistoryService;

@Controller
public class CheckTaskController {
	@Autowired
	private UserhistoryService userHistoryService;

	@GetMapping(value = "/admin/check")
	public ModelAndView showCheckTasks() {

		List<Userhistory> userHistoryUncheckeTasks = userHistoryService.getUserHistoriesWithUncheckedTasks();
		List<Userhistory> userHistoryCheckeTasks = userHistoryService.getUserHistoriesWithCheckedTasks();

		ModelAndView mv = new ModelAndView("admin_check_tasks_tab");
		mv.addObject("uncheckedHistory", userHistoryUncheckeTasks);
		mv.addObject("checkedHistory", userHistoryCheckeTasks);

		return mv;
	}

	@GetMapping(value = "/admin/check/{id}")
	public ModelAndView checkTaskForm(@PathVariable Integer id) {
		// перевірити обране завдання
		Userhistory userhistory = userHistoryService.findOne(id);

		ModelAndView mv = new ModelAndView("admin_check_task");
		mv.addObject("history", userhistory);
		return mv;
	}

	@PostMapping(value = "/admin/check/{id}")
	public ModelAndView handleCheckForm(@PathVariable Integer id, @RequestParam Integer mark) {

		// поточний запис із історії завдань
		Userhistory userhistory = userHistoryService.findOne(id);
		userhistory.setMark(mark);
		userHistoryService.saveUserhistory(userhistory);

		ModelAndView mv = new ModelAndView("redirect:/admin/check");

		return mv;
	}

}
