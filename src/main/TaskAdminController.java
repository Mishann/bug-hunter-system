package com.app.controller.admin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.Answer;
import com.app.model.Task;
import com.app.service.TaskService;
import com.app.service.UserhistoryService;

@Controller
public class TaskAdminController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserhistoryService userHistoryService;

	@GetMapping(value = "/admin/create")
	public ModelAndView showCreateTaskForm() {

		Task newTask = new Task();


		ModelAndView mv = new ModelAndView("admin_create_task");
		mv.addObject("task", newTask);

		return mv;
	}
	
	@PostMapping(value = "/admin/create")
	public ModelAndView addNewTask(@ModelAttribute Task task, @RequestParam MultipartFile uploadFile,
			@RequestParam MultipartFile uploadFileDescription,
			@RequestParam String variant1,
			@RequestParam String variant2,
			@RequestParam String variant3,
			@RequestParam String variant4,
			@RequestParam String rightAnswer) {

		Answer answer = new Answer();
		answer.setAnswer(rightAnswer);
		answer.setVariants(variant1 + "#" + variant2 + "#" + variant3 + "#" + variant4);
		
		if(task.getTaskType().equals("Закрите"))
			answer.setAnswer(variant1);
		
		task.setCreationDate(new Date());

		taskService.save(task, answer);

		// якщо файлb не обрані
		if(!(uploadFile.getSize() == 0))
			taskService.saveAnsweFile(answer, uploadFile);
	
		if(!(uploadFileDescription.getSize() == 0))
			taskService.saveTaskFile(task, uploadFileDescription);

		ModelAndView mv = new ModelAndView("redirect:/admin/create");
		return mv;
	}
	
	
}
