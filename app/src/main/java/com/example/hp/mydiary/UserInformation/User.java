package com.example.hp.mydiary.UserInformation;

public class User {
    private String mPassword;
    private Boolean isSetPassword;
    private String mQuestion;
    private String mAnswer;

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Boolean getSetPassword() {
        return isSetPassword;
    }

    public void setSetPassword(Boolean setPassword) {
        isSetPassword = setPassword;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }
}
