package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.User;

public interface UserService {
	public void saveUser(User user);
	public List<User> findAllUsers();
	public void updateUser(User user);
	
	public User getTeacher();
	public List<User> findAllStudents();
	
	public User findByLogin(String login);
	public User findByEmail(String email);

	
	public boolean isUserExist(String login, String password);
	public User getByLoginAndPassword(String login, String password);
	public User findOne(Integer id);
	
	public Integer getCountCompletedTasksByUser(User u);
	public Double getAvarageMarkByUser(User u);
	
	public void saveUserProfileImage(User student, MultipartFile uploadfile);

}
