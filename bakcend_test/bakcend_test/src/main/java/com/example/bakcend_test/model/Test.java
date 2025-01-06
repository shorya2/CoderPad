package com.example.bakcend_test.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Test {

    @Id
    private String id; // Unique identifier for the test
    private String createdBy; // ID of the user who created the test
    private String testName; // Name of the test
    private String testDescription; // Description of the test
    private int duration; // Duration of the test in minutes

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Question> questions; // List of questions in the test

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "test_questions", // Name of the join table
            joinColumns = @JoinColumn(name = "test_id"), // Foreign key for Test
            inverseJoinColumns = @JoinColumn(name = "question_id") // Foreign key for Question
    )
    private List<Question> questions ; // List of questions in the test

    @PrePersist
    public void generateId() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
