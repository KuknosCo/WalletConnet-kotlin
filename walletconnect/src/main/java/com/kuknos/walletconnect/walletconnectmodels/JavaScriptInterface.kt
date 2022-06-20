package com.kuknos.walletconnect.walletconnectmodels

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.kuknos.walletconnect.WalletConnect

class JavaScriptInterface (var context: Context,var publicKey : String,var walletConnect : WalletConnect) {
    @JavascriptInterface
    fun connectRequest(data: String?) {
        Log.i("KuknosLog","data : "+data)
        data?.let {
            walletConnect.connect(it,publicKey,context,null)
        }
    }
}