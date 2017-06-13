package com.app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Answer;


public interface AnswerRepository extends PagingAndSortingRepository<Answer,Integer> {

}
