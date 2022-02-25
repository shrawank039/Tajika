package com.matrixdeveloper.tajika.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPIRecentTransactionModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("transcation_id")
    @Expose
    private String transcationId;
    @SerializedName("amount")
    @Expose
    private float amount;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("submit_date")
    @Expose
    private String submitDate;
    @SerializedName("balance_type")
    @Expose
    private String balanceType;
    private final static long serialVersionUID = 4259253791293893828L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTranscationId() {
        return transcationId;
    }

    public void setTranscationId(String transcationId) {
        this.transcationId = transcationId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

}