package com.quizapp.common.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQuestionRequest {

    public int getId() {
        return id;
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("direction")
    @Expose
    private boolean direction;

    @SerializedName("quiz_mode")
    @Expose
    private int quiz_mode;

    @SerializedName("quizlist_id")
    @Expose
    private int quizlist_id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public int getQuizMode() {
        return quiz_mode;
    }

    public void setQuizMode(int quiz_mode) {
        this.quiz_mode = quiz_mode;
    }

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
}
