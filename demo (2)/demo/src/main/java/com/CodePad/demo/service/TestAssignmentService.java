
package com.CodePad.demo.service;

import com.CodePad.demo.dto.AttemptStatistics;
import com.CodePad.demo.entity.AssignedTest;
import com.CodePad.demo.entity.AttemptedTest;
import com.CodePad.demo.exception.ResourceNotFoundException;
import com.CodePad.demo.repository.AssignedTestRepository;
import com.CodePad.demo.repository.AttemptedTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestAssignmentService {

    @Autowired
    private AssignedTestRepository assignedTestRepository;

    @Autowired
    private AttemptedTestRepository attemptedTestRepository;

    // 1. Assign Test to User
    public AssignedTest assignTest(AssignedTest assignedTest) {
        return assignedTestRepository.save(assignedTest);
    }

    // 2. Get Assigned Tests for User
    public List<AssignedTest> getAssignedTestsForUser(String userEmail) {
        return assignedTestRepository.findByUserEmail(userEmail);
    }

    // 3. Get Assigned Tests by Creator
    public List<AssignedTest> getAssignedTestsByCreator(int assignedBy) {
        return assignedTestRepository.findByAssignedBy(assignedBy);
    }

    // 4. Get Test Assignment by ID
    public AssignedTest getTestAssignmentById(Long assignmentId) {
        return assignedTestRepository.findById(assignmentId).orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", assignmentId));
    }

    // 5. Update Test Assignment
    public AssignedTest updateTestAssignment(Long assignmentId, AssignedTest updatedAssignment) {
        AssignedTest assignment = assignedTestRepository.findById(assignmentId).orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", assignmentId));
        assignment.setTestId(updatedAssignment.getTestId());
        assignment.setUserEmail(updatedAssignment.getUserEmail());
        assignment.setAssignedBy(updatedAssignment.getAssignedBy());
        return assignedTestRepository.save(assignment);
    }

    // 6. Delete Test Assignment
    public void deleteTestAssignment(Long assignmentId) {
        AssignedTest assignment = assignedTestRepository.findById(assignmentId).orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", assignmentId));
        assignedTestRepository.delete(assignment);
    }

    // 7. Record Test Attempt
    public AttemptedTest recordTestAttempt(AttemptedTest attempt) {
        return attemptedTestRepository.save(attempt);
    }

    // 8. Get User Attempts for a Test
    public List<AttemptedTest> getUserAttemptsForTest(Long testId, Long userId) {
        return attemptedTestRepository.findByTestIdAndUserId(testId, userId);
    }

    // 9. Get All Attempts by User
    public List<AttemptedTest> getAllAttemptsByUser(Long userId) {
        return attemptedTestRepository.findByUserId(userId);
    }

    // 10. Get All Attempts for a Test
    public List<AttemptedTest> getAllAttemptsForTest(Long testId) {
        return attemptedTestRepository.findByTestId(testId);
    }

    // 11. Get Attempt Statistics
    public AttemptStatistics getAttemptStatistics(Long testId, Long userId) {
        List<AttemptedTest> attempts = attemptedTestRepository.findByTestIdAndUserId(testId, userId);
        int totalScore = attempts.stream().mapToInt(AttemptedTest::getScore).sum();
        int questionCount = attempts.size();

        AttemptStatistics stats = new AttemptStatistics();
        stats.setUserId(userId);
        stats.setTestId(testId);
        stats.setTotalScore(totalScore);
        stats.setQuestionCount(questionCount);

        return stats;
    }
}
