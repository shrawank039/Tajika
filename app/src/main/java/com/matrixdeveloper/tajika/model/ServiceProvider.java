package com.matrixdeveloper.tajika.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceProvider implements Serializable
{

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("business_categories")
    @Expose
    private String businessCategories;
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
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("profileimage")
    @Expose
    private Object profileimage;
    private final static long serialVersionUID = -2745584234168265951L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getBusinessCategories() {
        return businessCategories;
    }

    public void setBusinessCategories(String businessCategories) {
        this.businessCategories = businessCategories;
    }

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Object getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(Object profileimage) {
        this.profileimage = profileimage;
    }

}