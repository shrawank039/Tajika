package com.matrixdeveloper.tajika.model

import com.google.gson.annotations.SerializedName

class ServiceList {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("service_category")
    var serviceCategory: Int? = null

    @SerializedName("service_name")
    var serviceName: String? = null

    @SerializedName("service_type")
    var serviceType: String? = null

    @SerializedName("service_description")
    var serviceDescription: String? = null

    @SerializedName("service_image")
    var serviceImage: String? = null

    @SerializedName("service_price")
    var servicePrice: Int? = null

    @SerializedName("status")
    var status: Int? = null
}