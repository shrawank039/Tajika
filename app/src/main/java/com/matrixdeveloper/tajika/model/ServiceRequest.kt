package com.matrixdeveloper.tajika.model

import com.google.gson.annotations.SerializedName

class ServiceRequest {
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

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null
}