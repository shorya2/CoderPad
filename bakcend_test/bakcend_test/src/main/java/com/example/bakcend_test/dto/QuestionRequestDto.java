package com.example.bakcend_test.dto;

public class QuestionRequestDto {
    private String id;
    private String questionDescription;
    private String quesType;
    private String difficulty;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String selectedAnswer;
    private String correctAnswer;
    private String ddlCommands;
    private String dmlCommands;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getQuesType() {
        return quesType;
    }

    public void setQuesType(String quesType) {
        this.quesType = quesType;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getDdlCommands() {
        return ddlCommands;
    }

    public void setDdlCommands(String ddlCommands) {
        this.ddlCommands = ddlCommands;
    }

    public String getDmlCommands() {
        return dmlCommands;
    }

    public void setDmlCommands(String dmlCommands) {
        this.dmlCommands = dmlCommands;
    }
}
