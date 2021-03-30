package com.matrixdeveloper.tajika.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderProfileDetails implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("service_area")
    @Expose
    private String serviceArea;
    @SerializedName("business_categories")
    @Expose
    private String businessCategories;
    @SerializedName("service_description")
    @Expose
    private String serviceDescription;
    @SerializedName("year_of_experience")
    @Expose
    private String yearOfExperience;
    @SerializedName("bussiness_link")
    @Expose
    private String bussinessLink;
    @SerializedName("minimum_charge")
    @Expose
    private Integer minimumCharge;
    @SerializedName("education_level")
    @Expose
    private String educationLevel;
    @SerializedName("passportnumber")
    @Expose
    private String passportnumber;
    @SerializedName("upload_passportid")
    @Expose
    private String uploadPassportid;
    @SerializedName("professional_qualification")
    @Expose
    private String professionalQualification;
    @SerializedName("qualification_certification")
    @Expose
    private String qualificationCertification;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("online")
    @Expose
    private Integer online;
    @SerializedName("rating")
    @Expose
    private Double rating;
    private final static long serialVersionUID = -1318593204984058914L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getBusinessCategories() {
        return businessCategories;
    }

    public void setBusinessCategories(String businessCategories) {
        this.businessCategories = businessCategories;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(String yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getBussinessLink() {
        return bussinessLink;
    }

    public void setBussinessLink(String bussinessLink) {
        this.bussinessLink = bussinessLink;
    }

    public Integer getMinimumCharge() {
        return minimumCharge;
    }

    public void setMinimumCharge(Integer minimumCharge) {
        this.minimumCharge = minimumCharge;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getPassportnumber() {
        return passportnumber;
    }

    public void setPassportnumber(String passportnumber) {
        this.passportnumber = passportnumber;
    }

    public String getUploadPassportid() {
        return uploadPassportid;
    }

    public void setUploadPassportid(String uploadPassportid) {
        this.uploadPassportid = uploadPassportid;
    }

    public String getProfessionalQualification() {
        return professionalQualification;
    }

    public void setProfessionalQualification(String professionalQualification) {
        this.professionalQualification = professionalQualification;
    }

    public String getQualificationCertification() {
        return qualificationCertification;
    }

    public void setQualificationCertification(String qualificationCertification) {
        this.qualificationCertification = qualificationCertification;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
