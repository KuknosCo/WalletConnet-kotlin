package com.kuknos.walletconnect.walletconnectmodels

import com.google.gson.annotations.SerializedName

data class SocketClientModel(
    @SerializedName("project_id")
    var project_id: String,
    @SerializedName("meta")
    var meta: DappsMeta,
    @SerializedName("network")
    var network: String?
)