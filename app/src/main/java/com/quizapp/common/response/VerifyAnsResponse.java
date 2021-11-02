package com.quizapp.common.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyAnsResponse {

    @SerializedName("vocabulary")
    @Expose
    private boolean vocabulary;

    public boolean isVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(boolean vocabulary) {
        this.vocabulary = vocabulary;
    }
}
