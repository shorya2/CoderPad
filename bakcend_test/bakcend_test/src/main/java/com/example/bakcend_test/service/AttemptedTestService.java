package com.example.bakcend_test.service;

import com.example.bakcend_test.model.AttemptedQuestion;
import com.example.bakcend_test.model.AttemptedTest;
import com.example.bakcend_test.model.Question;
import com.example.bakcend_test.repository.AttemptedTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttemptedTestService {

    @Autowired
    private AttemptedTestRepository attemptedTestRepository;

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String USER;

    @Value("${spring.datasource.password}")
    private String PASSWORD;


    // Fetch all attempted tests
    public List<AttemptedTest> getAllAttemptedTests() {
        return attemptedTestRepository.findAll();
    }

    // Fetch an attempted test by ID
    public AttemptedTest getAttemptedTestById(String id) {
        return attemptedTestRepository.findById(id).orElseThrow(() -> new RuntimeException("Attempted Test not found with ID: " + id));
    }

    // Submit an attempted test
//    public AttemptedTest submitAttemptedTest(AttemptedTest attemptedTest) {
//        return attemptedTestRepository.save(attemptedTest);
//    }

//    public AttemptedTest submitAttemptedTest(AttemptedTest attemptedTest) {
//        List<AttemptedQuestion> attemptedQuestions = new ArrayList<>();
//
//        for (Question question : attemptedTest.getQuestions()) {
//            AttemptedQuestion attemptedQuestion = new AttemptedQuestion();
//            attemptedQuestion.setAttemptedTest(attemptedTest);
//            attemptedQuestion.setQuestionDescription(question.getQuestionDescription());
//            attemptedQuestion.setSelectedAnswer(question.getSelectedAnswer());
//            attemptedQuestion.setCorrectAnswer(question.getCorrectAnswer());
//
//            boolean isCorrect = question.getSelectedAnswer() != null
//                    && question.getSelectedAnswer().equals(question.getCorrectAnswer());
//            attemptedQuestion.setCorrect(isCorrect);
//
//            attemptedQuestions.add(attemptedQuestion);
//        }
//
//        attemptedTest.setAttemptedQuestions(attemptedQuestions);
//        return attemptedTestRepository.save(attemptedTest);
//    }

    // Submit an attempted test
    public AttemptedTest submitAttemptedTest(AttemptedTest attemptedTest) {
        List<AttemptedQuestion> attemptedQuestions = new ArrayList<>();

        for (Question question : attemptedTest.getQuestions()) {
            AttemptedQuestion attemptedQuestion = new AttemptedQuestion();
            attemptedQuestion.setAttemptedTest(attemptedTest);
            attemptedQuestion.setQuestionDescription(question.getQuestionDescription());
            attemptedQuestion.setSelectedAnswer(question.getSelectedAnswer());
            attemptedQuestion.setCorrectAnswer(question.getCorrectAnswer());



            // First check if the selectedAnswer and correctAnswer match directly
            boolean isCorrect = false;
            if (question.getSelectedAnswer() != null && question.getSelectedAnswer().equals(question.getCorrectAnswer())) {
                isCorrect = true;  // No need for validation if they match directly
            } else {
                // Use the SQLCompiler to validate the selected and correct answers
                QueryValidationResult validationResult = validateSQLQuery(question.getSelectedAnswer(), question.getCorrectAnswer());
                // Set isCorrect based on validation result if the direct match failed
                isCorrect = validationResult.isMatch();
            }

            // Set the isCorrect flag
            attemptedQuestion.setCorrect(isCorrect);

            attemptedQuestions.add(attemptedQuestion);
        }

        attemptedTest.setAttemptedQuestions(attemptedQuestions);
        attemptedTest.setAttemptedDate(LocalDateTime.now());
        return attemptedTestRepository.save(attemptedTest);
    }

    // Method to validate SQL query and compare results
    private QueryValidationResult validateSQLQuery(String userQuery, String correctQuery) {
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

    // Executes SQL query and returns the result
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

    // Compares two query results and returns whether they match
    private boolean compareResults(List<List<String>> correctResult, List<List<String>> userResult) {
        // Add comparison logic here. For now, assuming that if the size is the same, the results match.
        return correctResult.equals(userResult);
    }

    // SQLValidationResult Class
    public static class QueryValidationResult {
        private String message;
        private List<List<String>> correctQueryResult;
        private List<List<String>> userQueryResult;

        public QueryValidationResult(String message, List<List<String>> correctQueryResult, List<List<String>> userQueryResult) {
            this.message = message;
            this.correctQueryResult = correctQueryResult;
            this.userQueryResult = userQueryResult;
        }
        public boolean isMatch() {
            // Null check before comparing results
            if (correctQueryResult == null || userQueryResult == null) {
                return false;
            }
            return correctQueryResult.equals(userQueryResult);
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
