package com.example.bakcend_test.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class AssignedTest {
    @Id
    private String id; // Unique identifier for the assigned test
    private String testId;
    private String createdBy; // ID of the user who assigned the test
    private String testName; // Name of the assigned test
    private String testDescription; // Description of the assigned test
    private int duration; // Duration of the assigned test in minutes
    private String email;

    //    @OneToMany(cascade = CascadeType.ALL)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions; // List of questions in the assigned test

    // Getters and Setters

    @PrePersist
    public void generateId() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString(); // Generate a unique ID
        }
    }
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
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
