package com.crisis.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.crisis.demo.model.Submission;

@Repository
public interface SubmissionRepository extends MongoRepository<Submission, String> {

}