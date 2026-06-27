package com.verbalwala.backend.repository;

import com.verbalwala.backend.entity.Assessment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssessmentRepository
        extends MongoRepository<Assessment, String> {

}