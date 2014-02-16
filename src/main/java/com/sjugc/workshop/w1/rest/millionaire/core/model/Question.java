package com.sjugc.workshop.w1.rest.millionaire.core.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Simple model of one instance of question.<br />
 * <br />
 * <b>NOTE:</b>
 * <ul>
 * <li>{@link JsonIgnore @JsonIgnore} annotation on filed getter indicate that that field should not
 * be parsed to output JSON.</li>
 * </ul>
 * 
 * @author mjovanovic
 */
public class Question {

    /**
     * Question status
     */
    private QuestionStatus questionStatus;

    /**
     * Question text
     */
    private String text;

    /**
     * {@link List} of possible answers.
     */
    private List<String> answers;

    /**
     * Index of correct answer in list of possible answers.
     */
    private Integer correct;

    public Question() {}

    public Question( String text, List<String> answers, Integer correct, QuestionStatus questionStatus ) {
        this.text = text;
        this.answers = answers;
        this.correct = correct;
        this.questionStatus = questionStatus;
    }

    @JsonIgnore
    public QuestionStatus getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus( QuestionStatus questionStatus ) {
        this.questionStatus = questionStatus;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers( List<String> answers ) {
        this.answers = answers;
    }

    @JsonIgnore
    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect( Integer correct ) {
        this.correct = correct;
    }

}
