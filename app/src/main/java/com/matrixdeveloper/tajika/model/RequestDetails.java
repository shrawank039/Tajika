package com.matrixdeveloper.tajika.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestDetails implements Serializable
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
    private String willingAmountPay;
    @SerializedName("service_fees")
    @Expose
    private String serviceFees;
    @SerializedName("commission_fees")
    @Expose
    private String commissionFees;
    @SerializedName("total_amount_pay")
    @Expose
    private String totalAmountPay;
    @SerializedName("work_description")
    @Expose
    private String workDescription;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
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
    private String requestDeclinedDatetime;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("contact_person_phone")
    @Expose
    private String contactPersonPhone;
    @SerializedName("serviceaddress_building_no")
    @Expose
    private String serviceaddressBuildingNo;
    @SerializedName("serviceaddress_streetaddress")
    @Expose
    private String serviceaddressStreetaddress;
    @SerializedName("serviceaddress_landmark")
    @Expose
    private String serviceaddressLandmark;
    @SerializedName("instruction")
    @Expose
    private String instruction;
    @SerializedName("booking_datetime")
    @Expose
    private String bookingDatetime;
    @SerializedName("cancelation_reason")
    @Expose
    private String cancelationReason;
    @SerializedName("cancelation_comment")
    @Expose
    private String cancelationComment;
    @SerializedName("cancelation_date")
    @Expose
    private String cancelationDate;
    @SerializedName("cancelation_time")
    @Expose
    private String cancelationTime;
    @SerializedName("cancelled_by")
    @Expose
    private String cancelledBy;
    @SerializedName("cancellation_charges")
    @Expose
    private String cancellationCharges;
    @SerializedName("competed_on")
    @Expose
    private String competedOn;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("service_provider_image")
    @Expose
    private String serviceProviderImage;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
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
    @SerializedName("service_tax_fee")
    @Expose
    private Double serviceTaxFee;
    @SerializedName("adminpayableamount")
    @Expose
    private Double adminpayableamount;
    @SerializedName("user_rating")
    @Expose
    private Float userRating;
    private final static long serialVersionUID = -4720370573774085753L;

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

    public String getWillingAmountPay() {
        return willingAmountPay;
    }

    public void setWillingAmountPay(String willingAmountPay) {
        this.willingAmountPay = willingAmountPay;
    }

    public String getServiceFees() {
        return serviceFees;
    }

    public void setServiceFees(String serviceFees) {
        this.serviceFees = serviceFees;
    }

    public String getCommissionFees() {
        return commissionFees;
    }

    public void setCommissionFees(String commissionFees) {
        this.commissionFees = commissionFees;
    }

    public String getTotalAmountPay() {
        return totalAmountPay;
    }

    public void setTotalAmountPay(String totalAmountPay) {
        this.totalAmountPay = totalAmountPay;
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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
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

    public String getRequestDeclinedDatetime() {
        return requestDeclinedDatetime;
    }

    public void setRequestDeclinedDatetime(String requestDeclinedDatetime) {
        this.requestDeclinedDatetime = requestDeclinedDatetime;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getServiceaddressBuildingNo() {
        return serviceaddressBuildingNo;
    }

    public void setServiceaddressBuildingNo(String serviceaddressBuildingNo) {
        this.serviceaddressBuildingNo = serviceaddressBuildingNo;
    }

    public String getServiceaddressStreetaddress() {
        return serviceaddressStreetaddress;
    }

    public void setServiceaddressStreetaddress(String serviceaddressStreetaddress) {
        this.serviceaddressStreetaddress = serviceaddressStreetaddress;
    }

    public String getServiceaddressLandmark() {
        return serviceaddressLandmark;
    }

    public void setServiceaddressLandmark(String serviceaddressLandmark) {
        this.serviceaddressLandmark = serviceaddressLandmark;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getBookingDatetime() {
        return bookingDatetime;
    }

    public void setBookingDatetime(String bookingDatetime) {
        this.bookingDatetime = bookingDatetime;
    }

    public String getCancelationReason() {
        return cancelationReason;
    }

    public void setCancelationReason(String cancelationReason) {
        this.cancelationReason = cancelationReason;
    }

    public String getCancelationComment() {
        return cancelationComment;
    }

    public void setCancelationComment(String cancelationComment) {
        this.cancelationComment = cancelationComment;
    }

    public String getCancelationDate() {
        return cancelationDate;
    }

    public void setCancelationDate(String cancelationDate) {
        this.cancelationDate = cancelationDate;
    }

    public String getCancelationTime() {
        return cancelationTime;
    }

    public void setCancelationTime(String cancelationTime) {
        this.cancelationTime = cancelationTime;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancellationCharges() {
        return cancellationCharges;
    }

    public void setCancellationCharges(String cancellationCharges) {
        this.cancellationCharges = cancellationCharges;
    }

    public String getCompetedOn() {
        return competedOn;
    }

    public void setCompetedOn(String competedOn) {
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

    public String getServiceProviderImage() {
        return serviceProviderImage;
    }

    public void setServiceProviderImage(String serviceProviderImage) {
        this.serviceProviderImage = serviceProviderImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public Double getServiceTaxFee() {
        return serviceTaxFee;
    }

    public void setServiceTaxFee(Double serviceTaxFee) {
        this.serviceTaxFee = serviceTaxFee;
    }

    public Double getAdminpayableamount() {
        return adminpayableamount;
    }

    public void setAdminpayableamount(Double adminpayableamount) {
        this.adminpayableamount = adminpayableamount;
    }

    public Float getUserRating() {
        return userRating;
    }

    public void setUserRating(Float userRating) {
        this.userRating = userRating;
    }

}