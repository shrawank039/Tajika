package com.matrixdeveloper.tajika.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("from_user_id")
    @Expose
    private Integer fromUserId;
    @SerializedName("to_user_id")
    @Expose
    private Integer toUserId;
    @SerializedName("service_request_id")
    @Expose
    private Integer service_request_id;
    @SerializedName("notificationtext")
    @Expose
    private String notificationtext;
    @SerializedName("submit_date")
    @Expose
    private String submitDate;
    @SerializedName("read_status")
    @Expose
    private Integer readStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("actiontext")
    @Expose
    private String actiontext;
    private final static long serialVersionUID = 8693141791740510328L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getService_request_id() {
        return service_request_id;
    }

    public void setService_request_id(Integer service_request_id) {
        this.service_request_id = service_request_id;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getNotificationtext() {
        return notificationtext;
    }

    public void setNotificationtext(String notificationtext) {
        this.notificationtext = notificationtext;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActiontext() {
        return actiontext;
    }

    public void setActiontext(String actiontext) {
        this.actiontext = actiontext;
    }

}