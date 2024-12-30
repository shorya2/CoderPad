package com.CodePad.demo.dto;

import lombok.Data;

@Data
public class TestAssignmentRequestDTO {
    private Long testId;
    private String testName;
    private String userEmail; // User to whom the test is assigned
    private int assignedBy; // ID of the user assigning the test

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(int assignedBy) {
        this.assignedBy = assignedBy;
    }
}
