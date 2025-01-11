package com.example.bakcend_test.service;

import com.example.bakcend_test.model.Question;
import com.example.bakcend_test.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String USER;

    @Value("${spring.datasource.password}")
    private String PASSWORD;

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

    public String executeDdlCommands(List<String> ddlCommands) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                for (String ddlCommand : ddlCommands) {
                    stmt.executeUpdate(ddlCommand); // Execute DDL command
                }
                return "All DDL commands executed successfully!";
            } catch (SQLException e) {
                return "Error executing DDL commands: " + e.getMessage();
            }
        } catch (SQLException e) {
            return "Error connecting to database: " + e.getMessage();
        }
    }


    // Handle DML commands (e.g., INSERT, UPDATE, DELETE)
    public String executeDmlCommands(List<String> dmlCommands) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                for (String dmlCommand : dmlCommands) {
                    stmt.executeUpdate(dmlCommand); // Execute DML command
                }
                return "All DML commands executed successfully!";
            } catch (SQLException e) {
                return "Error executing DML commands: " + e.getMessage();
            }
        } catch (SQLException e) {
            return "Error connecting to database: " + e.getMessage();
        }
    }




}
