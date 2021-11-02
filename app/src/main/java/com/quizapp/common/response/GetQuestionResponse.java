package com.quizapp.common.response;

import java.util.List;

public class GetQuestionResponse {

    private int id;

    private String pattern;

    private List<String> questions;

    private String sentence_1;

    private String vocabulary;

    private String vocabulary_2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public String getSentence1() {
        return sentence_1;
    }

    public void setSentence1(String sentence_1) {
        this.sentence_1 = sentence_1;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getVocabulary2() {
        return vocabulary_2;
    }

    public void setVocabulary2(String vocabulary_2) {
        this.vocabulary_2 = vocabulary_2;
    }
}
