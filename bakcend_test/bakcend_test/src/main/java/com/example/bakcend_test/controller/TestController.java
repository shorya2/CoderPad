package com.example.bakcend_test.controller;

import com.example.bakcend_test.model.Test;
import com.example.bakcend_test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    @Autowired
    private TestService testService;

    // Fetch all tests
    @GetMapping
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    // Fetch a test by ID
    @GetMapping("/{id}")
    public Optional<Test> getTestById(@PathVariable String id) {
        return testService.getTestById(id);
    }

    // Create a new test
    @PostMapping
    public Test createTest(@RequestBody Test test) {
        return testService.createTest(test);
    }

    // Update an existing test
    @PutMapping("/{id}")
    public Test updateTest(@PathVariable String id, @RequestBody Test updatedTest) {
        return testService.updateTest(id, updatedTest);
    }

    // Delete a test by ID
    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable String id) {
        testService.deleteTest(id);
    }

    // Endpoint to add a global question to a test
    @PostMapping("/{testId}/add-question/{questionId}")
    public Test addQuestionToTest(@PathVariable String testId, @PathVariable String questionId) {
        return testService.addQuestionToTest(testId, questionId);
    }
}
