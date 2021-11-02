package com.quizapp.common.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainListItem {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("is_public")
    @Expose
    private Integer is_public;

    @SerializedName("language_1")
    @Expose
    private Language language_1;

    @SerializedName("language_2")
    @Expose
    private Language language_2;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("role")
    @Expose
    private Integer role;

    @SerializedName("translation_count")
    @Expose
    private Integer translation_count;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("user_id")
    @Expose
    private Integer user_id;

    @SerializedName("")
    @Expose
    private User user;

    @Override
    public String toString() {
        return name + " (Translations: " + String.valueOf(translation_count) + ")";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsPublic() {
        return is_public;
    }

    public void setIsPublic(Integer is_public) {
        this.is_public = is_public;
    }

    public Language getLanguage1() {
        return language_1;
    }

    public void setLanguage1(Language language_1) {
        this.language_1 = language_1;
    }

    public Language getLanguage2() {
        return language_2;
    }

    public void setLanguage2(Language language_2) {
        this.language_2 = language_2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getTranslationCount() {
        return translation_count;
    }

    public void setTranslationCount(Integer translation_count) {
        this.translation_count = translation_count;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
