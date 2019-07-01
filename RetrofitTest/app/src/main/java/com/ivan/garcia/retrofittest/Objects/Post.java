
package com.ivan.garcia.retrofittest.Objects;


import com.google.gson.annotations.SerializedName;

public class Post implements Cloneable{

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

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
