package com.matrixdeveloper.tajika.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRequestDetails implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("service_date")
    @Expose
    private String serviceDate;
    @SerializedName("service_time")
    @Expose
    private String serviceTime;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("willing_amount_pay")
    @Expose
    private Integer willingAmountPay;
    @SerializedName("work_description")
    @Expose
    private String workDescription;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("booking_id")
    @Expose
    private Object bookingId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("service_provider_id")
    @Expose
    private Integer serviceProviderId;
    @SerializedName("request_date")
    @Expose
    private String requestDate;
    @SerializedName("request_time")
    @Expose
    private String requestTime;
    @SerializedName("request_accept_date")
    @Expose
    private String requestAcceptDate;
    @SerializedName("request_accept_time")
    @Expose
    private String requestAcceptTime;
    @SerializedName("request_declined_datetime")
    @Expose
    private Object requestDeclinedDatetime;
    @SerializedName("contact_person_name")
    @Expose
    private Object contactPersonName;
    @SerializedName("contact_person_phone")
    @Expose
    private Object contactPersonPhone;
    @SerializedName("serviceaddress_building_no")
    @Expose
    private Object serviceaddressBuildingNo;
    @SerializedName("serviceaddress_streetaddress")
    @Expose
    private Object serviceaddressStreetaddress;
    @SerializedName("serviceaddress_landmark")
    @Expose
    private Object serviceaddressLandmark;
    @SerializedName("instruction")
    @Expose
    private Object instruction;
    @SerializedName("booking_datetime")
    @Expose
    private Object bookingDatetime;
    @SerializedName("cancelation_reason")
    @Expose
    private Object cancelationReason;
    @SerializedName("cancelation_comment")
    @Expose
    private Object cancelationComment;
    @SerializedName("cancelation_date")
    @Expose
    private Object cancelationDate;
    @SerializedName("cancelation_time")
    @Expose
    private Object cancelationTime;
    @SerializedName("competed_on")
    @Expose
    private Object competedOn;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("customername")
    @Expose
    private String customername;
    @SerializedName("customerphone")
    @Expose
    private String customerphone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("adminpayableamount")
    @Expose
    private Integer adminpayableamount;
    private final static long serialVersionUID = -4955905707896560318L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getWillingAmountPay() {
        return willingAmountPay;
    }

    public void setWillingAmountPay(Integer willingAmountPay) {
        this.willingAmountPay = willingAmountPay;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getBookingId() {
        return bookingId;
    }

    public void setBookingId(Object bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestAcceptDate() {
        return requestAcceptDate;
    }

    public void setRequestAcceptDate(String requestAcceptDate) {
        this.requestAcceptDate = requestAcceptDate;
    }

    public String getRequestAcceptTime() {
        return requestAcceptTime;
    }

    public void setRequestAcceptTime(String requestAcceptTime) {
        this.requestAcceptTime = requestAcceptTime;
    }

    public Object getRequestDeclinedDatetime() {
        return requestDeclinedDatetime;
    }

    public void setRequestDeclinedDatetime(Object requestDeclinedDatetime) {
        this.requestDeclinedDatetime = requestDeclinedDatetime;
    }

    public Object getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(Object contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public Object getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(Object contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public Object getServiceaddressBuildingNo() {
        return serviceaddressBuildingNo;
    }

    public void setServiceaddressBuildingNo(Object serviceaddressBuildingNo) {
        this.serviceaddressBuildingNo = serviceaddressBuildingNo;
    }

    public Object getServiceaddressStreetaddress() {
        return serviceaddressStreetaddress;
    }

    public void setServiceaddressStreetaddress(Object serviceaddressStreetaddress) {
        this.serviceaddressStreetaddress = serviceaddressStreetaddress;
    }

    public Object getServiceaddressLandmark() {
        return serviceaddressLandmark;
    }

    public void setServiceaddressLandmark(Object serviceaddressLandmark) {
        this.serviceaddressLandmark = serviceaddressLandmark;
    }

    public Object getInstruction() {
        return instruction;
    }

    public void setInstruction(Object instruction) {
        this.instruction = instruction;
    }

    public Object getBookingDatetime() {
        return bookingDatetime;
    }

    public void setBookingDatetime(Object bookingDatetime) {
        this.bookingDatetime = bookingDatetime;
    }

    public Object getCancelationReason() {
        return cancelationReason;
    }

    public void setCancelationReason(Object cancelationReason) {
        this.cancelationReason = cancelationReason;
    }

    public Object getCancelationComment() {
        return cancelationComment;
    }

    public void setCancelationComment(Object cancelationComment) {
        this.cancelationComment = cancelationComment;
    }

    public Object getCancelationDate() {
        return cancelationDate;
    }

    public void setCancelationDate(Object cancelationDate) {
        this.cancelationDate = cancelationDate;
    }

    public Object getCancelationTime() {
        return cancelationTime;
    }

    public void setCancelationTime(Object cancelationTime) {
        this.cancelationTime = cancelationTime;
    }

    public Object getCompetedOn() {
        return competedOn;
    }

    public void setCompetedOn(Object competedOn) {
        this.competedOn = competedOn;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerphone() {
        return customerphone;
    }

    public void setCustomerphone(String customerphone) {
        this.customerphone = customerphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAdminpayableamount() {
        return adminpayableamount;
    }

    public void setAdminpayableamount(Integer adminpayableamount) {
        this.adminpayableamount = adminpayableamount;
    }

}
