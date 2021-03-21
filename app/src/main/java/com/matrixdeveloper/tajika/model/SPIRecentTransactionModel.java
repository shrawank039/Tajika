package com.matrixdeveloper.tajika.model;

public class SPIRecentTransactionModel {
    int id;
    String transactionId;
    String transactionDate;
    String debitAmount;

    public SPIRecentTransactionModel(int id, String transactionId, String transactionDate, String debitAmount) {
        this.id = id;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.debitAmount = debitAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
