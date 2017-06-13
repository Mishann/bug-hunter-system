package com.app.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Task;
import com.app.model.User;
import com.app.model.Userhistory;
import com.app.repository.TaskRepository;
import com.app.repository.UserHistoryRepository;
import com.app.repository.UserRepository;
import com.app.service.UserhistoryService;

@Service
public class UserhistoryImpl implements UserhistoryService {

	@Autowired
	private UserHistoryRepository userHistoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public void saveUserhistory(Userhistory userhistory) {
		userHistoryRepository.save(userhistory);
	}

	@Override
	public List<Userhistory> findAllUserhistories() {
		List<Userhistory> list= new ArrayList<>();
		
		for (Userhistory userhistory : userHistoryRepository.findAll())
			list.add(userhistory);
		
		return list;
	}

	@Override
	public void updateUserhistory(Userhistory userhistory) {
		userHistoryRepository.save(userhistory);
	}

	@Override
	public void saveAnswerFile(Userhistory userhistory, MultipartFile file) {
		
		if(file == null)
			return;
		
		File directory = new File(
				new File("src\\main\\resources\\static\\files").getAbsolutePath() +
				"\\user\\answers\\" +userhistory.getUser().getId()+"\\" + userhistory.getTask().getId() );

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
		userhistory.setFileLink("/files/user/answers/"+ userhistory.getUser().getId()+"/"+ userhistory.getTask().getId() + "/"+ file.getOriginalFilename());

		userhistory.getUser().getUserhistories().add(userhistory);
		userhistory.getTask().getUserhistories().add(userhistory);

		userRepository.save(userhistory.getUser());
		taskRepository.save(userhistory.getTask());
		
		
	}

	@Override
	public List<Userhistory> getUserHistoriesWithUncheckedTasks() {
	
		return findAllUserhistories()
				.stream()
				.filter(h->h.getMark() == null && h.getTask().getTaskType().equals("Відкрите"))
				.collect(Collectors.toList());
	}

	@Override
	public List<Userhistory> getUserHistoriesWithCheckedTasks() {
		return findAllUserhistories()
				.stream()
				.filter(h->h.getMark() != null && h.getTask().getTaskType().equals("Відкрите"))
				.collect(Collectors.toList());
	}

	@Override
	public Userhistory findOne(Integer id) {
		return userHistoryRepository.findOne(id);
	}

	@Override
	public List< Userhistory> getUserHistoryWithUnchekedTaskByUser(User u) {
		
		return findAllUserhistories()
				.stream()
				.filter(h->h.getMark() == null && h.getUser().getId() == u.getId() && h.getTask().getTaskType().equals("Відкрите"))
				.collect(Collectors.toList());
	}

	@Override
	public List< Userhistory> getUserHistoryWithChekedTaskByUser(User u) {
		return findAllUserhistories()
				.stream()
				.filter(h->h.getMark() != null && h.getUser().getId() == u.getId() )
				.collect(Collectors.toList());
	}
}
