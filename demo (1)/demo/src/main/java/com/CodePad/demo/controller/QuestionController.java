package com.CodePad.demo.controller;
import com.CodePad.demo.dto.QuestionRequestDTO;
import com.CodePad.demo.entity.QuestionMaster;
import com.CodePad.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tests/{testId}/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public QuestionMaster addQuestion(@PathVariable Long testId,
                                      @RequestBody QuestionRequestDTO questionRequest) {
        return questionService.addQuestion(testId, questionRequest);
    }

    @GetMapping
    public List<QuestionMaster> getQuestionsByTest(@PathVariable Long testId) {
        return questionService.getQuestionsByTest(testId);
    }

    @PutMapping("/{questionId}")
    public QuestionMaster updateQuestion(@PathVariable Long testId,
                                         @PathVariable Long questionId,
                                         @RequestBody QuestionRequestDTO questionRequest) {
        return questionService.updateQuestion(testId, questionId, questionRequest);
    }

    @DeleteMapping("/{questionId}")
    public void deleteQuestion(@PathVariable Long testId, @PathVariable Long questionId) {
        questionService.deleteQuestion(testId, questionId);
    }
}
