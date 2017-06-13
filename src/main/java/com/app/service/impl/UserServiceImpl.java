package com.app.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.User;
import com.app.model.Userhistory;
import com.app.repository.UserHistoryRepository;
import com.app.repository.UserRepository;
import com.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserHistoryRepository userHistoryRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	@Override
	public List<User> findAllUsers() {
		List<User> usersList = new ArrayList<>();
		for (User user : this.userRepository.findAll()) {
			usersList.add(user);
		}
		return usersList;
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	public User getTeacher() {
		return userRepository.getTeacher();
	}

	@Override
	public boolean isUserExist(String login, String password) {

		List<User> allUsers = new ArrayList<>();

		allUsers = this.findAllUsers().stream().filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
				.collect(Collectors.toList());

		if (!allUsers.isEmpty())
			return true;

		return false;
	}

	@Override
	public User findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User getByLoginAndPassword(String login, String password) {

		Optional<User> user = findAllUsers().stream()
				.filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password)).findFirst();

		return user.get();
	}

	@Override
	public void saveUserProfileImage(User student, MultipartFile uploadfile) {
		
		if(uploadfile == null)
			return;
		
		File newFile = new File("src\\main\\resources\\static\\images\\user\\"+ student.getId());
		
		if(!newFile.exists()){
			newFile.mkdir();
		}
		
		String path = newFile.getAbsolutePath()+"\\"+ uploadfile.getOriginalFilename();
				
		try {

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));

			stream.write(uploadfile.getBytes());
			stream.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		student.setImageLink("/images/user/" + student.getId() + "/" + uploadfile.getOriginalFilename());
		updateUser(student);

	}

	@Override
	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}

	@Override
	public List<User> findAllStudents() {
		return findAllUsers()
				.stream()
				.filter(u->u.getRole().equals("student"))
				.collect(Collectors.toList());
	}

	@Override
	public Integer getCountCompletedTasksByUser(User u) {
		
		Iterable<Userhistory> iterableHistories = userHistoryRepository.findAll();
		List<Userhistory> listHistories = new ArrayList<>();
		
		for (Userhistory userhistory : iterableHistories) {
			listHistories.add(userhistory);
		}
	
		// історія завдань поточного користувача 
		listHistories =  listHistories
						.stream()
						.filter(h->h.getUser().getId() == u.getId() && h.getMark()!=null)
						.collect(Collectors.toList());
	
		return listHistories.size();
	}

	@Override
	public Double getAvarageMarkByUser(User u) {

		Iterable<Userhistory> iterableHistories = userHistoryRepository.findAll();
		List<Userhistory> listHistories = new ArrayList<>();
		
		for (Userhistory userhistory : iterableHistories) {
			listHistories.add(userhistory);
		}
	
		// історія завдань поточного користувача 
		listHistories =  listHistories
						.stream()
						.filter(h->h.getUser().getId() == u.getId() && h.getMark() != null)
						.collect(Collectors.toList());
	
		if(listHistories.isEmpty())
			return 0d;
		
		Double amount = 0d;
		
		for (Userhistory userhistory : listHistories) {
			if(userhistory.getMark() != null){
				amount = amount + userhistory.getMark();
			}
			
		}
		
		Double res = (int)(amount / listHistories.size() * 100) /100.0;
		
		return res;
	}


}
