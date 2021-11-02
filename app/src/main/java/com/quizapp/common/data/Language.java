package com.quizapp.common.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Language {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("language_id")
    @Expose
    private String language_id;

    @SerializedName("language_name")
    @Expose
    private String language_name;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("")
    @Expose
    private String updated_at;
}
