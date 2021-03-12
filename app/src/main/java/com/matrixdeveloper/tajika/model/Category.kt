package com.matrixdeveloper.tajika.model

import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("status")
    var status: Int? = null
}