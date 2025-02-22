package com.example.bakcend_test.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class AttemptedTest {

    @Id
    private String id; // Unique identifier for the attempted test
    private String candidateId; // ID of the candidate who attempted the test
    private String testId; // ID of the test that was attempted

    @Transient
    private List<Question> questions; // This is only for input and not stored in the database

    @OneToMany(mappedBy = "attemptedTest", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevent infinite recursion
    private List<AttemptedQuestion> attemptedQuestions; // Processed questions stored in the DB

    @PrePersist
    public void generateId() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString(); // Generate a unique ID
        }
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<AttemptedQuestion> getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(List<AttemptedQuestion> attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }
}