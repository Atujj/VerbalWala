package com.verbalwala.backend.service;

import com.verbalwala.backend.entity.AssessmentAttempt;
import com.verbalwala.backend.entity.User;

public interface StudentSecurityService {

    User getCurrentStudent();

    AssessmentAttempt getStudentAttempt(String attemptId);

}