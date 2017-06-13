package com.app.controller.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.app.model.Answer;
import com.app.model.Task;
import com.app.model.User;
import com.app.model.Userhistory;
import com.app.service.TaskService;
import com.app.service.UserService;
import com.app.service.UserhistoryService;
import com.app.utils.Info;
import com.app.utils.TaskHelper;

@Controller
public class TaskUserController {
	@Autowired
	UserService userService;

	@Autowired
	TaskService taskService;

	@Autowired
	UserhistoryService userhistoryService;

	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@PostConstruct
	public void init() {
		requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
	}

	@GetMapping("/user/tasks")
	public ModelAndView showTasksPage(@RequestParam(required = false) String caption) {
		ModelAndView mv = new ModelAndView("user_tasks_tab");

		List<Task> tasks = taskService.findAllTasks();
		// якщо є пошук
		if (caption != null) {
			tasks = taskService.findAllTasks().stream()
					.filter(t -> t.getShortDescription().contains(caption.toLowerCase())).collect(Collectors.toList());
		}

		mv.addObject("allTasks", tasks);
		return mv;
	}

	@GetMapping("/user/tasks/{id}")
	public ModelAndView showTaskForm(@PathVariable Integer id, @RequestParam(required = false) Integer historyId) {
		ModelAndView mv = new ModelAndView("user_task");

		Task currTask = taskService.getTask(id);
		Answer currAnswer = currTask.getAnswer();

		mv.addObject("task", currTask);
		mv.addObject("answer", currAnswer);

		if (historyId != null) {
			// для того щоб користувач чужі відповіді не переглядав
			User u = userhistoryService.findOne(historyId).getUser();
			if (u.getId() != Info.currentUser.getId()) {
				return new ModelAndView("redirect:/user/tasks");

			}
			mv.addObject("historyId", historyId);
			mv.addObject("markOnClosedTask", userhistoryService.findOne(historyId).getMark());
		}

		// якщо запитання закритого типу
		if (currTask.getTaskType().equals("Закрите")) {
			List<String> variants = TaskHelper.parseAndShuffle(currAnswer.getVariants(), "#");

			mv.addObject("variant1", variants.get(0));
			mv.addObject("variant2", variants.get(1));
			mv.addObject("variant3", variants.get(2));
			mv.addObject("variant4", variants.get(3));

		} else {

		}
		return mv;
	}

	@PostMapping("/user/tasks/{id}")
	public ModelAndView handleUserAnswer(@PathVariable Integer id, @RequestParam MultipartFile uploadfile,
			@RequestParam(required = false) String userAnswer, @RequestParam(required = false) String variant) {

		Task currTask = taskService.getTask(id);
		Answer currAnswer = currTask.getAnswer();

		// якщо закрите
		// звірити відповідь із оригіналом

		Userhistory userhistory = new Userhistory();
		if (currTask.getTaskType().equals("Закрите")) {
			userhistory.setAnswer(variant);
			if (variant.equals(currAnswer.getAnswer()))
				userhistory.setMark(10);
			else
				userhistory.setMark(0);
		} else {
			userhistory.setAnswer(userAnswer);

		}

		userhistory.setUser(Info.currentUser);
		userhistory.setTask(currTask);

		userhistoryService.saveUserhistory(userhistory);
		if (!(uploadfile.getSize() == 0))
			userhistoryService.saveAnswerFile(userhistory, uploadfile);

		return new ModelAndView("redirect:/user/tasks/{id}?historyId=" + userhistory.getId());
	}
}
