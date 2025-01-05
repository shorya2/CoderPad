package com.example.bakcend_test.service;

import com.example.bakcend_test.model.AttemptedTest;
import com.example.bakcend_test.repository.AttemptedTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttemptedTestService {

    @Autowired
    private AttemptedTestRepository attemptedTestRepository;

    // Fetch all attempted tests
    public List<AttemptedTest> getAllAttemptedTests() {
        return attemptedTestRepository.findAll();
    }

    // Fetch an attempted test by ID
    public AttemptedTest getAttemptedTestById(String id) {
        return attemptedTestRepository.findById(id).orElseThrow(() -> new RuntimeException("Attempted Test not found with ID: " + id));
    }

    // Submit an attempted test
    public AttemptedTest submitAttemptedTest(AttemptedTest attemptedTest) {
        return attemptedTestRepository.save(attemptedTest);
    }
}
