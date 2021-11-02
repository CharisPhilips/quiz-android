package com.quizapp.common.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyQuestionRequest {

    @SerializedName("quizlist_id")
    @Expose
    private  int quizlist_id;

    @SerializedName("user_id")
    @Expose
    private  int user_id;

    @SerializedName("vocabulary")
    @Expose
    private  String vocabulary;

    @SerializedName("direction")
    @Expose
    private  boolean direction;

    public int getQuizlistId() {
        return quizlist_id;
    }

    public void setQuizlistId(int quizlist_id) {
        this.quizlist_id = quizlist_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

}
