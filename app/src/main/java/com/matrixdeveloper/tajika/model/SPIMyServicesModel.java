package com.matrixdeveloper.tajika.model;

public class SPIMyServicesModel {
    int id;
    String serviceNumber;
    String serviceCategory;
    String serviceExperience;
    String serviceMinCharge;

    public SPIMyServicesModel(int id, String serviceNumber, String serviceCategory, String serviceExperience, String serviceMinCharge) {
        this.id = id;
        this.serviceNumber = serviceNumber;
        this.serviceCategory = serviceCategory;
        this.serviceExperience = serviceExperience;
        this.serviceMinCharge = serviceMinCharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getServiceExperience() {
        return serviceExperience;
    }

    public void setServiceExperience(String serviceExperience) {
        this.serviceExperience = serviceExperience;
    }

    public String getServiceMinCharge() {
        return serviceMinCharge;
    }

    public void setServiceMinCharge(String serviceMinCharge) {
        this.serviceMinCharge = serviceMinCharge;
    }
}

