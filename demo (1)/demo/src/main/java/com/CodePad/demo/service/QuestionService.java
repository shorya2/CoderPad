package com.CodePad.demo.service;

import com.CodePad.demo.dto.QuestionRequestDTO;
import com.CodePad.demo.entity.QuestionMaster;
import com.CodePad.demo.entity.TestMaster;
import com.CodePad.demo.repository.QuestionRepository;
import com.CodePad.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    // MySQL Database connection settings
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db"; 
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public QuestionMaster addQuestion(Long testId, QuestionRequestDTO questionRequest) {
        TestMaster test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        QuestionMaster question = new QuestionMaster();
        question.setTest(test);
        question.setQuestionDescription(questionRequest.getQuestionDescription());
        question.setQuesType(questionRequest.getQuesType());
        question.setDifficulty(questionRequest.getDifficulty());
        question.setCorrectAnswer(questionRequest.getCorrectAnswer());
        question.setQueryResult(questionRequest.getQueryResult());

        // Validate SQL query by comparing the result against the correct query result
        QueryValidationResult validationResult = validateSQLQuery(questionRequest.getQueryResult(), questionRequest.getCorrectAnswer(), testId);
        question.setQueryResult(validationResult.getMessage());  // Set the validation message in the queryResult field

        // Return the question object after saving
        return questionRepository.save(question);
    }

    // Validate SQL query by comparing the result with the correct query
    private QueryValidationResult validateSQLQuery(String userQuery, String correctQuery, Long testId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Execute the correct query
            List<List<String>> correctQueryResult = executeQuery(conn, correctQuery);

            // Execute the user's query
            List<List<String>> userQueryResult = executeQuery(conn, userQuery);

            // Console log the results for both queries
            System.out.println("Correct Query Result: ");
            printQueryResult(correctQueryResult, correctQuery);

            System.out.println("User Query Result: ");
            printQueryResult(userQueryResult, userQuery);

            // If both results are empty, they do not match
            if (correctQueryResult.isEmpty() && userQueryResult.isEmpty()) {
                return new QueryValidationResult(
                        "Both the user query and the correct query returned empty results. This is not a match.",
                        correctQueryResult, userQueryResult
                );
            }

            // Compare the result sets (ignoring order but checking actual data)
            if (compareResults(correctQueryResult, userQueryResult)) {
                return new QueryValidationResult(
                        "The SQL query result matches the correct query.",
                        correctQueryResult, userQueryResult
                );
            } else {
                return new QueryValidationResult(
                        "The SQL query result does not match the correct query.",
                        correctQueryResult, userQueryResult
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new QueryValidationResult("Error executing SQL queries: " + e.getMessage(), null, null);
        }
    }

    // Execute the SQL query and return the result as a list of rows (each row is a list of columns)
    private List<List<String>> executeQuery(Connection conn, String query) throws Exception {
        List<List<String>> result = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Get the number of columns in the result set
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Process each row of the result
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));  // Add each column value to the row
                }
                result.add(row);
            }
        }
        return result;
    }

    // Print the query result in the console for debugging, including column names
    private void printQueryResult(List<List<String>> queryResult, String query) {
        try {
            if (queryResult.isEmpty()) {
                System.out.println("No rows returned for the query: " + query);
            } else {
                // Print column names for reference (we can extract column names if needed)
                System.out.println("Query: " + query);
                for (List<String> row : queryResult) {
                    System.out.println(row);  // Print all columns in the row
                }
            }
        } catch (Exception e) {
            System.out.println("Error while printing query result: " + e.getMessage());
        }
    }

    // Compare two result sets (ignoring order of rows)
    private boolean compareResults(List<List<String>> correctResult, List<List<String>> userResult) {
        Set<List<String>> correctSet = new HashSet<>(correctResult);
        Set<List<String>> userSet = new HashSet<>(userResult);

        return correctSet.equals(userSet);  // Compare the sets, ignoring order
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
        question.setCorrectAnswer(questionRequest.getCorrectAnswer());
        question.setQueryResult(questionRequest.getQueryResult());

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

    // Custom class to hold the query validation result and both query results (correct and user)
    public static class QueryValidationResult {
        private String message;
        private List<List<String>> correctQueryResult;
        private List<List<String>> userQueryResult;

        public QueryValidationResult(String message, List<List<String>> correctQueryResult, List<List<String>> userQueryResult) {
            this.message = message;
            this.correctQueryResult = correctQueryResult;
            this.userQueryResult = userQueryResult;
        }

        public String getMessage() {
            return message;
        }

        public List<List<String>> getCorrectQueryResult() {
            return correctQueryResult;
        }

        public List<List<String>> getUserQueryResult() {
            return userQueryResult;
        }
    }
}
