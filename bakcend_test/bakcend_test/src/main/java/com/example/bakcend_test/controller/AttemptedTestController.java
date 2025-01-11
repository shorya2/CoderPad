package com.example.bakcend_test.controller;
import com.example.bakcend_test.model.AttemptedTest;
import com.example.bakcend_test.service.AttemptedTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/attempted-tests")
public class AttemptedTestController {

    @Autowired
    private AttemptedTestService attemptedTestService;

    // Fetch all attempted tests
    @GetMapping
    public List<AttemptedTest> getAllAttemptedTests() {
        return attemptedTestService.getAllAttemptedTests();
    }

    // Fetch an attempted test by ID
    @GetMapping("/{id}")
    public AttemptedTest getAttemptedTestById(@PathVariable String id) {
        return attemptedTestService.getAttemptedTestById(id);
    }

    // Submit an attempted test
    @PostMapping
    public AttemptedTest submitAttemptedTest(@RequestBody AttemptedTest attemptedTest) {
        return attemptedTestService.submitAttemptedTest(attemptedTest);
    }


}
