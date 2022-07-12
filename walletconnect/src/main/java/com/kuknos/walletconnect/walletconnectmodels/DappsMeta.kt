package com.kuknos.walletconnect.walletconnectmodels

import com.google.gson.annotations.SerializedName

data class DappsMeta(
    @SerializedName("title")
    var title:String?,
    @SerializedName("logo")
    var logo:String?,
    @SerializedName("url")
    var url:String?
)