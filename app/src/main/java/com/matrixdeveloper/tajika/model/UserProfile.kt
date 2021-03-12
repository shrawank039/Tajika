package com.matrixdeveloper.tajika.model

import com.google.gson.annotations.SerializedName

class UserProfile {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("uuid")
    var uuid: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("phone")
    var phone: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("is_active")
    var isActive: String? = null

    @SerializedName("token")
    var token: Any? = null

    @SerializedName("deleted_at")
    var deletedAt: Any? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("role")
    var role: Int? = null

    @SerializedName("profileimage")
    var profileimage: Any? = null

    @SerializedName("last_login")
    var lastLogin: Any? = null
}