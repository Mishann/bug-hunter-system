package com.app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.Task;

@Transactional
public interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {

}
