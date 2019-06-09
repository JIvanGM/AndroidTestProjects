
package com.ivan.garcia.retrofittest.Objects;


import com.google.gson.annotations.SerializedName;

public class Post {

    private int id;
    private String userId;
    private String title;

    @SerializedName("body")
    private String text;

    public Post(String title, String text, String userId) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
