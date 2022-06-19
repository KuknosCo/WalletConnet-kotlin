package com.kuknos.walletconnect.walletconnectmodels

import com.google.gson.annotations.SerializedName

data class SocketRequestModel(
    @SerializedName("type")
    var type : String?,
    @SerializedName("client")
    var client : SocketClientModel?,
    @SerializedName("data")
    var data : Any?
)