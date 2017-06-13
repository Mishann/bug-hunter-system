package com.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.model.Answer;
import com.app.model.Task;
import com.app.model.User;


public interface TaskService {
	public void save(Task task, Answer answer);
	public List<Task> findAllTasks();
	public void update(Task task, Answer answer);
	
	public void saveAnsweFile(Answer answer, MultipartFile file);
	public void saveTaskFile(Task task, MultipartFile file);
	
	public Task getTask(Integer id);
	public Answer getAnswer(Integer id);
	
	public List<Answer> findAllAnswers();
}
