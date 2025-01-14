package com.example.bakcend_test.controller;

import com.example.bakcend_test.dto.QuestionRequestDto;
import com.example.bakcend_test.model.Question;
import com.example.bakcend_test.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
    @RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Endpoint to create a new question
    @PostMapping
    public Question createQuestion(@RequestBody QuestionRequestDto qdto) {
        Question question=new Question();
        question.setId(qdto.getId());
        question.setQuestionDescription(qdto.getQuestionDescription());
        question.setQuesType(qdto.getQuesType());
        question.setDifficulty(qdto.getDifficulty());
        question.setOption1(qdto.getOption1());
        question.setOption2(qdto.getOption2());
        question.setOption3(qdto.getOption3());
        question.setOption4(qdto.getOption4());
        question.setSelectedAnswer(qdto.getSelectedAnswer());
        question.setCorrectAnswer(qdto.getCorrectAnswer());

        if(qdto.getQuesType().equals("sql")) {
            String[] ddl = qdto.getDdlcommands().split(";");
            List<String> ddlList = Arrays.asList(ddl);
            questionService.executeDdlCommands(ddlList);
        }

        if(qdto.getQuesType().equals("sql")) {
            String[] dml = qdto.getDmlcommands().split(";");
            List<String> dmlList = Arrays.asList(dml);
            questionService.executeDmlCommands(dmlList);
        }

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
