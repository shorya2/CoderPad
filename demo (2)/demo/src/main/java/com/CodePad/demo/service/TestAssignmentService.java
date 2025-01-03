
package com.CodePad.demo.service;

import com.CodePad.demo.dto.AttemptStatistics;
import com.CodePad.demo.entity.AssignedTest;
import com.CodePad.demo.entity.AttemptedTest;
import com.CodePad.demo.exception.ResourceNotFoundException;
import com.CodePad.demo.repository.AssignedTestRepository;
import com.CodePad.demo.repository.AttemptedTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.*;
import java.util.ArrayList;

@Service
public class TestAssignmentService
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

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

    // 7. Record Test Attempt and Validate SQL Query
    public AttemptedTest recordTestAttempt(AttemptedTest attempt) {
        String userQuery = attempt.getOptionSelected();  // The query user submits
        String correctAnswerQuery = attempt.getCorrectOption();  // Correct query from the correctOption field

        // Use the SQLCompiler to validate the user query
        QueryValidationResult validationResult = validateSQLQuery(userQuery, correctAnswerQuery);

        // Set the score based on whether the query results match or not
        if (validationResult.isMatch()) {
            attempt.setScore(1);  // If the query results match, user gets a score of 1
        } else {
            attempt.setScore(0);  // If it doesn't match, the score is 0
        }


        // Store both correct query result and user's query result for reference
        attempt.setCorrectOption(validationResult.getCorrectQueryResult().toString());  // Store correct result
        attempt.setOptionSelected(validationResult.getUserQueryResult().toString());   // Store user result

        // Save and return the attempt
        return attemptedTestRepository.save(attempt);
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
            // This can be modified to compare the query results in a more sophisticated way if needed
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
