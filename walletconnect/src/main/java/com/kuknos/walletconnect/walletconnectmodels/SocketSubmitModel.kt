package com.kuknos.walletconnect.walletconnectmodels


import com.google.gson.annotations.SerializedName

data class SocketSubmitModel(
    @SerializedName("data")
    var data: SocketResponseModel?,
    @SerializedName("project_id")
    var projectId: String?
)