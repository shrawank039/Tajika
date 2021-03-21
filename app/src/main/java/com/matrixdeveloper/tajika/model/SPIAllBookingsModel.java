package com.matrixdeveloper.tajika.model;

public class SPIAllBookingsModel {
    int id;
    String bookingID;
    String customerName;
    String serviceDate;
    String serviceType;
    String status;
    String completedOn;

    public SPIAllBookingsModel(int id, String bookingID, String customerName, String serviceDate, String serviceType, String status, String completedOn) {
        this.id = id;
        this.bookingID = bookingID;
        this.customerName = customerName;
        this.serviceDate = serviceDate;
        this.serviceType = serviceType;
        this.status = status;
        this.completedOn = completedOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }
}
