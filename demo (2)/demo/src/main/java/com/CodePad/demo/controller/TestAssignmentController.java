package com.CodePad.demo.controller;
import com.CodePad.demo.dto.AttemptStatistics;
import com.CodePad.demo.entity.AssignedTest;
import com.CodePad.demo.entity.AttemptedTest;
import com.CodePad.demo.service.TestAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/assignments")
public class TestAssignmentController {

    @Autowired
    private TestAssignmentService assignmentService;

    // Assign a test
    @PostMapping
    public AssignedTest assignTest(@RequestBody AssignedTest assignedTest) {
        return assignmentService.assignTest(assignedTest);
    }

    // Get assigned tests for a user
    @GetMapping("/user")
    public List<AssignedTest> getAssignedTestsForUser(@RequestParam String userEmail) {
        return assignmentService.getAssignedTestsForUser(userEmail);
    }

    // Get assigned tests by creator
    @GetMapping("/creator")
    public List<AssignedTest> getAssignedTestsByCreator(@RequestParam int assignedBy) {
        return assignmentService.getAssignedTestsByCreator(assignedBy);
    }

    // Record a test attempt
    @PostMapping("/attempt")
    public AttemptedTest recordTestAttempt(@RequestBody AttemptedTest attempt) {
        return assignmentService.recordTestAttempt(attempt);
    }

    // Get user attempts for a test
    @GetMapping("/attempts")
    public List<AttemptedTest> getUserAttemptsForTest(@RequestParam Long testId, @RequestParam Long userId) {
        return assignmentService.getUserAttemptsForTest(testId, userId);
    }

    // Get attempt statistics
    @GetMapping("/attempts/stats")
    public AttemptStatistics getAttemptStatistics(@RequestParam Long testId, @RequestParam Long userId) {
        return assignmentService.getAttemptStatistics(testId, userId);
    }
}
