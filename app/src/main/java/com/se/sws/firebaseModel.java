package com.se.sws;

/**
 * Represents the format way we receive the information from firebase
 */
public class firebaseModel {
    private String title; // Title of post
    private String content; // Content of post
    private String phone; // Phone number of the person
    private String uid; // Name of the post uploader

    public firebaseModel() {
    }

    public firebaseModel(String title, String content, String phone, String uid){
        this.title = title;
        this.content = content;
        this.phone = phone;
        this.uid = uid;
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

    public String getUid() { return uid; }

    public void setAuthor(String author) { this.uid = author;}


}
