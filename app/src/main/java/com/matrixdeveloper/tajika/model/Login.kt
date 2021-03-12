package com.matrixdeveloper.tajika.model

import com.google.gson.annotations.SerializedName

class Login {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("uuid")
    var uuid: String? = null

    @SerializedName("token")
    var token: String? = null

    @SerializedName("roles")
    var roles: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("phone_number")
    var phoneNumber: String? = null
}