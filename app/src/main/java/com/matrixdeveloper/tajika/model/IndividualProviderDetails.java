package com.matrixdeveloper.tajika.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndividualProviderDetails implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("profileimage")
    @Expose
    private Object profileimage;
    @SerializedName("business_categories")
    @Expose
    private String businessCategories;
    @SerializedName("service_area")
    @Expose
    private String serviceArea;
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
    private String minimumCharge;
    @SerializedName("education_level")
    @Expose
    private String educationLevel;
    @SerializedName("passportnumber")
    @Expose
    private Object passportnumber;
    @SerializedName("professional_qualification")
    @Expose
    private Object professionalQualification;
    @SerializedName("upload_passportid")
    @Expose
    private String uploadPassportid;
    @SerializedName("qualification_certification")
    @Expose
    private String qualificationCertification;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("job_completed")
    @Expose
    private Integer jobCompleted;
    private final static long serialVersionUID = 1832106901506120018L;

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

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Object getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(Object profileimage) {
        this.profileimage = profileimage;
    }

    public String getBusinessCategories() {
        return businessCategories;
    }

    public void setBusinessCategories(String businessCategories) {
        this.businessCategories = businessCategories;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
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

    public String getMinimumCharge() {
        return minimumCharge;
    }

    public void setMinimumCharge(String minimumCharge) {
        this.minimumCharge = minimumCharge;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Object getPassportnumber() {
        return passportnumber;
    }

    public void setPassportnumber(Object passportnumber) {
        this.passportnumber = passportnumber;
    }

    public Object getProfessionalQualification() {
        return professionalQualification;
    }

    public void setProfessionalQualification(Object professionalQualification) {
        this.professionalQualification = professionalQualification;
    }

    public String getUploadPassportid() {
        return uploadPassportid;
    }

    public void setUploadPassportid(String uploadPassportid) {
        this.uploadPassportid = uploadPassportid;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getJobCompleted() {
        return jobCompleted;
    }

    public void setJobCompleted(Integer jobCompleted) {
        this.jobCompleted = jobCompleted;
    }

}
