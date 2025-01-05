package com.example.bakcend_test.service;


import com.example.bakcend_test.model.AssignedTest;
import com.example.bakcend_test.repository.AssignedTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignedTestService {

    @Autowired
    private AssignedTestRepository assignedTestRepository;

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
}
