package com.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.app.model.Task;
import com.app.model.User;
import com.app.model.Userhistory;

public interface UserhistoryService {
	
	public void saveUserhistory(Userhistory userhistory);
	public List<Userhistory> findAllUserhistories();
	public void updateUserhistory(Userhistory userhistory);
	public void saveAnswerFile(Userhistory userhistory, MultipartFile file);
	
	public Userhistory findOne(Integer id);
	
	public List< Userhistory> getUserHistoryWithUnchekedTaskByUser(User u);
	public List< Userhistory> getUserHistoryWithChekedTaskByUser(User u);
	
	public List<Userhistory> getUserHistoriesWithUncheckedTasks();
	public List<Userhistory> getUserHistoriesWithCheckedTasks();	
}
