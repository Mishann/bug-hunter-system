package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.User;

@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.role = 'teacher'")	
	public User getTeacher();
	public User findByLogin(String login);
	public User findByEmail(String email);	
}
