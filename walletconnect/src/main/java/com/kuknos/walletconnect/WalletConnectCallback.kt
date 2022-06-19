package com.kuknos.walletconnect

interface WalletConnectCallback {
    fun onRequestListener(projectId: String, actionType: String, data: Any)
    fun onConnected()
    fun onDisconnected()
}