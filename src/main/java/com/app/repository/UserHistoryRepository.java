package com.app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Userhistory;

public interface UserHistoryRepository extends PagingAndSortingRepository<Userhistory, Integer> {

}
