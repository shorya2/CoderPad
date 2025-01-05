package com.example.bakcend_test.controller;

import com.example.bakcend_test.model.Question;
import com.example.bakcend_test.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
    @RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Endpoint to create a new question
    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    // Endpoint to get all questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Endpoint to get a question by ID
    @GetMapping("/{id}")
    public Optional<Question> getQuestionById(@PathVariable String id) {
        return questionService.getQuestionById(id);
    }

    // Endpoint to update a question
    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable String id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }
}
