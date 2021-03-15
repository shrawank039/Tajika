package com.matrixdeveloper.tajika.model

import java.io.Serializable

open class BaseBean : Serializable {

    var isWebError: Boolean = false
    var status: String = ""
    var error: String = ""
    var errorMsg: String = ""
    var webMessage: String = ""


}
