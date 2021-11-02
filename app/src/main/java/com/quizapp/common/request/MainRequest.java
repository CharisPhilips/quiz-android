package com.quizapp.common.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainRequest {

    public Object getId() {
        return id;
    }

    @SerializedName("id")
    @Expose
    private Object id;

    @SerializedName("is_public")
    @Expose
    private boolean is_public;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    public void setId(Object id) {
        this.id = id;
    }

    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
