package com.matrixdeveloper.tajika.model;

public class VoucherModel {
    private int id;
    private int rating;
    private String voucherHeader;
    private String voucherMinOrder;
    private String voucherValidity;
    private String voucherCode;

    public VoucherModel(int id, int rating, String voucherHeader, String voucherMinOrder, String voucherValidity, String voucherCode) {
        this.id = id;
        this.rating = rating;
        this.voucherHeader = voucherHeader;
        this.voucherMinOrder = voucherMinOrder;
        this.voucherValidity = voucherValidity;
        this.voucherCode = voucherCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getVoucherHeader() {
        return voucherHeader;
    }

    public void setVoucherHeader(String voucherHeader) {
        this.voucherHeader = voucherHeader;
    }

    public String getVoucherMinOrder() {
        return voucherMinOrder;
    }

    public void setVoucherMinOrder(String voucherMinOrder) {
        this.voucherMinOrder = voucherMinOrder;
    }

    public String getVoucherValidity() {
        return voucherValidity;
    }

    public void setVoucherValidity(String voucherValidity) {
        this.voucherValidity = voucherValidity;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

}
