package com.kuknos.walletconnect.walletconnectmodels

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class SocketResponseModel(
    @SerializedName("status")
    var status:String,
    @SerializedName("message")
    var message:String?,
    @SerializedName("type")
    var type:String?,
    @SerializedName("data")
    var data:String?
)
