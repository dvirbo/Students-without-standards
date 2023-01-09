package com.se.sws.Model;

/**
 * Represents the format way we receive the information from firebase
 */
public class Post {
    private String title; // Title of post
    private String content; // Content of post
    private String phone; // Phone number of the person
    private String uid; // id of the post uploader
    private String name; // Name of the post uploader

    public Post() {
    }

    public Post(String title, String content, String phone, String uid, String name){
        this.title = title;
        this.content = content;
        this.phone = phone;
        this.uid = uid;
        this.name = name;
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

    public String getName() { return name;}

    public void setName(String name) {this.name = name;}
/*
+ String getTitle
+void setTitle(String title)
+String getContent(String content)
+String getPhone()
+ void setPhone(String phone)
+ void setPhone
+ void setAuthor
 */

}
