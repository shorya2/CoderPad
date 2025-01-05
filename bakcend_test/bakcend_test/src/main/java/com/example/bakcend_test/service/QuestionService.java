package com.example.bakcend_test.service;

import com.example.bakcend_test.model.Question;
import com.example.bakcend_test.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // Method to create a new question
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Method to get all questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Method to get a question by ID
    public Optional<Question> getQuestionById(String id) {
        return questionRepository.findById(id);
    }

    // Method to update a question
    public Question updateQuestion(String id, Question question) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found");
        }
        question.setId(id);
        return questionRepository.save(question);
    }
}
