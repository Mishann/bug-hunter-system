package com.app.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Answer;
import com.app.model.Task;
import com.app.repository.AnswerRepository;
import com.app.repository.TaskRepository;
import com.app.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public void save(Task task, Answer answer) {
		task.setAnswer(answer);
		taskRepository.save(task);
	}

	@Override
	public List<Task> findAllTasks() {
		Iterable<Task> iterableTask = taskRepository.findAll();
		List<Task> listTasks = new ArrayList<>();

		for (Task task : iterableTask)
			listTasks.add(task);

		return listTasks;
	}

	@Override
	public void update(Task task, Answer answer) {
		taskRepository.save(task);
		answerRepository.save(answer);
	}

	@Override
	public List<Answer> findAllAnswers() {
		Iterable<Answer> iterableAnswer = answerRepository.findAll();
		List<Answer> listAnswers = new ArrayList<>();

		for (Answer answer : iterableAnswer)
			listAnswers.add(answer);

		return listAnswers;

	}

	@Override
	public Task getTask(Integer id) {
		return taskRepository.findOne(id);
	}

	@Override
	public Answer getAnswer(Integer id) {
		return answerRepository.findOne(id);
	}

	@Override
	public void saveAnsweFile(Answer answer, MultipartFile uploadFile) {

		if(uploadFile == null)
			return;
		
		File directory = new File(
				new File("src\\main\\resources\\static\\files").getAbsolutePath() + "\\admin\\answers\\" + answer.getId());

		if (!directory.exists())
			directory.mkdirs();

		try {

			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(directory + "\\" + uploadFile.getOriginalFilename()));

			stream.write(uploadFile.getBytes());
			stream.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		answer.setFileLink("/files/admin/answers/"+ answer.getId()+"/"+ uploadFile.getOriginalFilename());		
		this.answerRepository.save(answer);
	}

	@Override
	public void saveTaskFile(Task task, MultipartFile file) {
		if(file == null)
			return;
		
		File directory = new File(
				new File("src\\main\\resources\\static\\files").getAbsolutePath() + "\\admin\\tasks\\" + task.getId());

		if (!directory.exists())
			directory.mkdirs();

		try {

			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(directory + "\\" + file.getOriginalFilename()));

			stream.write(file.getBytes());
			stream.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		task.setFileLink("/files/admin/tasks/"+ task.getId()+"/"+ file.getOriginalFilename());
		this.taskRepository.save(task);
	}

}
