package com.se.sws;

/**
 * Represents the format way we receive the information from firebase
 */
public class firebaseModel {
    private String title; // Title of post
    private String content; // Content of post
    private String phone; // Phone number of the person

    public firebaseModel() {
    }

    public firebaseModel(String title, String content, String phone){
        this.title = title;
        this.content = content;
        this.phone = phone;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() { return phone;}

    public void setPhone(String phone) { this.phone = phone; }

}
