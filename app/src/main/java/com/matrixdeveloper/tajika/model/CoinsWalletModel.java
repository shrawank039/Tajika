package com.matrixdeveloper.tajika.model;

public class CoinsWalletModel {
    private int id;
    private int dcStatus;
    private String header;
    private String coins;
    private String date;

    public CoinsWalletModel(int id, int dcStatus, String header, String coins, String date) {
        this.id = id;
        this.dcStatus = dcStatus;
        this.header = header;
        this.coins = coins;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDcStatus() {
        return dcStatus;
    }

    public void setDcStatus(int dcStatus) {
        this.dcStatus = dcStatus;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
