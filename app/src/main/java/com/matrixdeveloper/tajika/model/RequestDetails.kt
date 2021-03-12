package com.matrixdeveloper.tajika.model

import com.google.gson.annotations.SerializedName

class RequestDetails {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("user_id")
    var userId: Int? = null

    @SerializedName("service_date")
    var serviceDate: String? = null

    @SerializedName("service_time")
    var serviceTime: String? = null

    @SerializedName("service_type")
    var serviceType: Int? = null

    @SerializedName("willing_amount_pay")
    var willingAmountPay: Int? = null

    @SerializedName("work_description")
    var workDescription: String? = null

    @SerializedName("request_id")
    var requestId: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("service_provider_id")
    var serviceProviderId: Int? = null

    @SerializedName("request_date")
    var requestDate: String? = null

    @SerializedName("request_time")
    var requestTime: String? = null

    @SerializedName("request_accept_date")
    var requestAcceptDate: Any? = null

    @SerializedName("request_accept_time")
    var requestAcceptTime: Any? = null

    @SerializedName("contact_person_name")
    var contactPersonName: Any? = null

    @SerializedName("contact_person_phone")
    var contactPersonPhone: Any? = null

    @SerializedName("serviceaddress_building_no")
    var serviceaddressBuildingNo: Any? = null

    @SerializedName("serviceaddress_streetaddress")
    var serviceaddressStreetaddress: Any? = null

    @SerializedName("serviceaddress_landmark")
    var serviceaddressLandmark: Any? = null

    @SerializedName("instruction")
    var instruction: Any? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null
}