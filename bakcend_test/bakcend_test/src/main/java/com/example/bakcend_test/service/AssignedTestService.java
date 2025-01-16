package com.example.bakcend_test.service;


import com.example.bakcend_test.model.AssignedTest;
import com.example.bakcend_test.model.Question;
import com.example.bakcend_test.repository.AssignedTestRepository;
import com.example.bakcend_test.repository.AttemptedTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AssignedTestService {

    @Autowired
    private AssignedTestRepository assignedTestRepository;

    @Autowired
    private AttemptedTestRepository attemptedTestRepository;

    // Fetch all assigned tests
    public List<AssignedTest> getAllAssignedTests() {
        return assignedTestRepository.findAll();
    }

    // Fetch an assigned test by ID
    public Optional<AssignedTest> getAssignedTestById(String id) {
        return assignedTestRepository.findById(id);
    }

    // Assign a new test
    public AssignedTest assignTest(AssignedTest assignedTest) {
        return assignedTestRepository.save(assignedTest);
    }

    // Remove an assigned test
    public void removeAssignedTest(String id) {
        assignedTestRepository.deleteById(id);
    }

    @Transactional
    public void removeAssignedTestByEmailAndCreatedBy(String email, String createdBy) {
        assignedTestRepository.deleteByEmailAndCreatedBy(email, createdBy);
    }
}