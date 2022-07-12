package com.kuknos.walletconnect

interface WalletConnectCallback {
    fun onRequestListener(projectId: String, actionType: String, data: Any,network:String?)
    fun onConnected()
    fun onDisconnected()
}