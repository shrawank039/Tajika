package com.matrixdeveloper.tajika.Model;

public class NotificationModel {
    private int id;
    private int notificationImage;
    private String notificationHeader;
    private String notificationContent;
    private String notificationFooter;

    public NotificationModel(int id, int notificationImage, String notificationHeader, String notificationContent, String notificationFooter) {
        this.id = id;
        this.notificationImage = notificationImage;
        this.notificationHeader = notificationHeader;
        this.notificationContent = notificationContent;
        this.notificationFooter = notificationFooter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(int notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getNotificationHeader() {
        return notificationHeader;
    }

    public void setNotificationHeader(String notificationHeader) {
        this.notificationHeader = notificationHeader;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public String getNotificationFooter() {
        return notificationFooter;
    }

    public void setNotificationFooter(String notificationFooter) {
        this.notificationFooter = notificationFooter;
    }
}
