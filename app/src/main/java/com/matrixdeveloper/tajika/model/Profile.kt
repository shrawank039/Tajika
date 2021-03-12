package com.matrixdeveloper.tajika.model

import com.google.gson.annotations.SerializedName

class Profile {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: Any? = null

    @SerializedName("phone")
    var phone: String? = null

    @SerializedName("role")
    var role: Int? = null

    @SerializedName("profileimage")
    var profileimage: Any? = null

    @SerializedName("business_categories")
    var businessCategories: String? = null

    @SerializedName("business_name")
    var businessName: String? = null

    @SerializedName("location")
    var location: String? = null

    @SerializedName("location_address")
    var locationAddress: String? = null

    @SerializedName("service_description")
    var serviceDescription: String? = null

    @SerializedName("service_offerd_image")
    var serviceOfferdImage: String? = null

    @SerializedName("bussiness_link")
    var bussinessLink: String? = null

    @SerializedName("service_offerd_videolink")
    var serviceOfferdVideolink: String? = null

    @SerializedName("latitude")
    var latitude: Any? = null

    @SerializedName("longitude")
    var longitude: Any? = null

    @SerializedName("distance")
    var distance: String? = null
}