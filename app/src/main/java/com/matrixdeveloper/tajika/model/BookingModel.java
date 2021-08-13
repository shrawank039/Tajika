package com.matrixdeveloper.tajika.model;

public class BookingModel {
    private String id;
    private int serviceImage;
    private String serviceName;
    private String serviceAddress;
    private String serviceType;
    private String serviceStatus;

    public BookingModel(String id, int serviceImage, String serviceName, String serviceAddress, String serviceType, String serviceStatus) {
        this.id = id;
        this.serviceImage = serviceImage;
        this.serviceName = serviceName;
        this.serviceAddress = serviceAddress;
        this.serviceType = serviceType;
        this.serviceStatus = serviceStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(int serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
