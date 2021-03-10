package com.matrixdeveloper.tajika.Model;

public class ConversationListModel {
    private int id;
    private int image;
    private String header;
    private String time;

    public ConversationListModel(int id, int image, String header, String time) {
        this.id = id;
        this.image = image;
        this.header = header;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}