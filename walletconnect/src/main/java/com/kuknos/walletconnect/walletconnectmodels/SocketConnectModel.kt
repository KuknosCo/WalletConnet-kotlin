package com.kuknos.walletconnect.walletconnectmodels

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class SocketConnectModel(
    @SerializedName("data")
    var data: JsonObject?,
    @SerializedName("project_id")
    var projectId: String?
)