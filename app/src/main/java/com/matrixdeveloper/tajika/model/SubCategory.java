package com.matrixdeveloper.tajika.model;


import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory implements Serializable
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("service_description")
    @Expose
    private String serviceDescription;
    @SerializedName("service_image")
    @Expose
    private String serviceImage;
    @SerializedName("service_price")
    @Expose
    private Integer servicePrice;
    @SerializedName("status")
    @Expose
    private Integer status;
    private final static long serialVersionUID = 5239476963329867599L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public Integer getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Integer servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
