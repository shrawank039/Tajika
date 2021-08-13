package com.matrixdeveloper.tajika.model;

public class SubscriptionModel {

    private String id;
    private String subType;
    private String subAmount;
    private String subDays;

    public SubscriptionModel(String id, String subType, String subAmount, String subDays) {
        this.id = id;
        this.subType = subType;
        this.subAmount = subAmount;
        this.subDays = subDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubAmount() {
        return subAmount;
    }

    public void setSubAmount(String subAmount) {
        this.subAmount = subAmount;
    }

    public String getSubDays() {
        return subDays;
    }

    public void setSubDays(String subDays) {
        this.subDays = subDays;
    }
}
