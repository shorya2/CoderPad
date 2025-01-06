package com.example.bakcend_test.service;

import com.example.bakcend_test.model.Question;
import com.example.bakcend_test.model.Test;
import com.example.bakcend_test.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionService questionService;

    // Fetch all tests
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // Fetch a test by ID
    public Optional<Test> getTestById(String id) {
        return testRepository.findById(id);
    }

    // Create a new test
    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    // Update an existing test
    public Test updateTest(String id, Test updatedTest) {
        return testRepository.findById(id).map(test -> {
            test.setTestName(updatedTest.getTestName());
            test.setTestDescription(updatedTest.getTestDescription());
            test.setDuration(updatedTest.getDuration());
            test.setQuestions(updatedTest.getQuestions());
            return testRepository.save(test);
        }).orElseThrow(() -> new RuntimeException("Test not found with ID: " + id));
    }

    // Delete a test by ID
    public void deleteTest(String id) {
        testRepository.deleteById(id);
    }

    // Method to add a question to a test
    public Test addQuestionToTest(String testId, String questionId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        // Fetch the question by ID from the global pool
        Question question = questionService.getQuestionById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Add the question to the test
        if (test.getQuestions().contains(question)) {
            throw new RuntimeException("Question is already added to this test.");
        }

        test.getQuestions().add(question);

        // Save and return the updated test
        return testRepository.save(test);
    }
}
