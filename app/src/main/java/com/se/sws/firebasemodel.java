package com.se.sws;

public class firebasemodel {
    private String title;
    private String content;
    private String phone;

    public firebasemodel() {
    }

    public firebasemodel (String title, String content, String phone){
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
