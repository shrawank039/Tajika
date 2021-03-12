package com.matrixdeveloper.tajika.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessInfo implements Serializable
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
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("location_address")
    @Expose
    private String locationAddress;
    @SerializedName("service_description")
    @Expose
    private String serviceDescription;
    @SerializedName("service_offerd_image")
    @Expose
    private String serviceOfferdImage;
    @SerializedName("bussiness_link")
    @Expose
    private String bussinessLink;
    @SerializedName("service_offerd_videolink")
    @Expose
    private String serviceOfferdVideolink;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("distance")
    @Expose
    private String distance;
    private final static long serialVersionUID = -6831958187799088630L;

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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceOfferdImage() {
        return serviceOfferdImage;
    }

    public void setServiceOfferdImage(String serviceOfferdImage) {
        this.serviceOfferdImage = serviceOfferdImage;
    }

    public String getBussinessLink() {
        return bussinessLink;
    }

    public void setBussinessLink(String bussinessLink) {
        this.bussinessLink = bussinessLink;
    }

    public String getServiceOfferdVideolink() {
        return serviceOfferdVideolink;
    }

    public void setServiceOfferdVideolink(String serviceOfferdVideolink) {
        this.serviceOfferdVideolink = serviceOfferdVideolink;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
