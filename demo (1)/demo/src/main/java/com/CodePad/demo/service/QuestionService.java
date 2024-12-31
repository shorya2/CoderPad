package com.CodePad.demo.service;

import com.CodePad.demo.dto.QuestionRequestDTO;
import com.CodePad.demo.entity.QuestionMaster;
import com.CodePad.demo.entity.TestMaster;
import com.CodePad.demo.repository.QuestionRepository;
import com.CodePad.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    public QuestionMaster addQuestion(Long testId, QuestionRequestDTO questionRequest) {
        TestMaster test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        QuestionMaster question = new QuestionMaster();
        question.setTest(test);
        question.setQuestionDescription(questionRequest.getQuestionDescription());
        question.setQuesType(questionRequest.getQuesType());
        question.setDifficulty(questionRequest.getDifficulty());
        question.setOption1(questionRequest.getOption1());
        question.setOption2(questionRequest.getOption2());
        question.setOption3(questionRequest.getOption3());
        question.setOption4(questionRequest.getOption4());
        question.setCorrectAnswer(questionRequest.getCorrectAnswer());
        question.setQueryResult(questionRequest.getQueryResult());
        return questionRepository.save(question);
    }

    public List<QuestionMaster> getQuestionsByTest(Long testId) {
        TestMaster test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));
        return questionRepository.findByTest(test);
    }

    // Update Question
    public QuestionMaster updateQuestion(Long testId, Long questionId, QuestionRequestDTO questionRequest) {
        TestMaster test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        QuestionMaster question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Check if the question belongs to the specified test
        if (!question.getTest().getId().equals(testId)) {
            throw new RuntimeException("This question does not belong to the specified test.");
        }

        question.setQuestionDescription(questionRequest.getQuestionDescription());
        question.setQuesType(questionRequest.getQuesType());
        question.setDifficulty(questionRequest.getDifficulty());
        question.setOption1(questionRequest.getOption1());
        question.setOption2(questionRequest.getOption2());
        question.setOption3(questionRequest.getOption3());
        question.setOption4(questionRequest.getOption4());
        question.setCorrectAnswer(questionRequest.getCorrectAnswer());
        question.setQueryResult(questionRequest.getQueryResult());
        question.setLastModified(LocalDateTime.now());

        return questionRepository.save(question);
    }

    // Delete Question
    public void deleteQuestion(Long testId, Long questionId) {
        TestMaster test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        QuestionMaster question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Check if the question belongs to the specified test
        if (!question.getTest().getId().equals(testId)) {
            throw new RuntimeException("This question does not belong to the specified test.");
        }

        questionRepository.delete(question);
    }
}
