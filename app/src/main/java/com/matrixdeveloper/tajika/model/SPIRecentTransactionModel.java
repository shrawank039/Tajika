package com.matrixdeveloper.tajika.model;

public class SPIRecentTransactionModel {
    String id;
    String transactionId;
    String transactionDate;
    String debitAmount;

    public SPIRecentTransactionModel(String id, String transactionId, String transactionDate, String debitAmount) {
        this.id = id;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.debitAmount = debitAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }
}
