package com.example.bakcend_test.controller;

import com.example.bakcend_test.model.AssignedTest;
import com.example.bakcend_test.service.AssignedTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assigned-tests")
public class AssignedTestController {

    @Autowired
    private AssignedTestService assignedTestService;

    // Fetch all assigned tests
    @GetMapping
    public List<AssignedTest> getAllAssignedTests() {
        return assignedTestService.getAllAssignedTests();
    }

    // Fetch an assigned test by ID
    @GetMapping("/{id}")
    public Optional<AssignedTest> getAssignedTestById(@PathVariable String id) {
        return assignedTestService.getAssignedTestById(id);
    }

    // Assign a new test
    @PostMapping
    public AssignedTest assignTest(@RequestBody AssignedTest assignedTest) {
        return assignedTestService.assignTest(assignedTest);
    }

    // Remove an assigned test
    @DeleteMapping("/{id}")
    public void removeAssignedTest(@PathVariable String id) {
        assignedTestService.removeAssignedTest(id);
    }

    @DeleteMapping("/by-email-and-createdBy")
    public void removeAssignedTestByEmailAndCreatedBy(@RequestParam String email, @RequestParam String createdBy) {
        assignedTestService.removeAssignedTestByEmailAndCreatedBy(email, createdBy);
    }
}
