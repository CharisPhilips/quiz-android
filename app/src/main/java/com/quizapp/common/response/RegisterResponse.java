package com.quizapp.common.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.quizapp.common.data.User;

public class RegisterResponse {

    @SerializedName("token")
    @Expose
    private String accessToken;

    @SerializedName("user")
    @Expose
    private User user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
