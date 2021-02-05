package com.matrixdeveloper.tajika.Model;

public class RedeemCoinModel {
    private int id;
    private int redeemRating;
    private String redeemHeader;
    private String redeemReqCoin;
    private String redeemValidity;

    public RedeemCoinModel(int id, int redeemRating, String redeemHeader, String redeemReqCoin, String redeemValidity) {
        this.id = id;
        this.redeemRating = redeemRating;
        this.redeemHeader = redeemHeader;
        this.redeemReqCoin = redeemReqCoin;
        this.redeemValidity = redeemValidity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRedeemRating() {
        return redeemRating;
    }

    public void setRedeemRating(int redeemRating) {
        this.redeemRating = redeemRating;
    }

    public String getRedeemHeader() {
        return redeemHeader;
    }

    public void setRedeemHeader(String redeemHeader) {
        this.redeemHeader = redeemHeader;
    }

    public String getRedeemReqCoin() {
        return redeemReqCoin;
    }

    public void setRedeemReqCoin(String redeemReqCoin) {
        this.redeemReqCoin = redeemReqCoin;
    }

    public String getRedeemValidity() {
        return redeemValidity;
    }

    public void setRedeemValidity(String redeemValidity) {
        this.redeemValidity = redeemValidity;
    }
}
