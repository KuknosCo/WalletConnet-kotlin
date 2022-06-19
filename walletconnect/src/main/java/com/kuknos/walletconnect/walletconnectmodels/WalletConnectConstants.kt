package com.kuknos.walletconnect.walletconnectmodels

class WalletConnectConstants {
    companion object{
        const val ACTION_GET_ACCOUNT = "account-publickey"
        const val ACTION_SIGN_DATA = "sign-data"
        const val ACTION_SIGN_XDR = "sign-xdr"
        const val ACTION_CHANGE_TRUST = "change-trust"
        const val ACTION_CREATE_ACCOUNT = "create-account"
        const val ACTION_CURVE_DECRYPT = "curve-decrypt"
        const val ACTION_PAYMENT = "payment"
        const val ACTION_BUY_TOKEN = "buy-token"
        const val ACTION_PING = "ping"
        const val ACTION_DISCONNECT = "disconnect"
        const val ACTION_WALLET_CONNECT_REQUEST = "wallet-connect-request"
        const val DISCONNECT_SOCKET = "disconnect"
        const val ACTION_SIGN_CMS = "pki-sign-cms"
        const val ACTION_SIGN_pdf = "pki-sign-pdf"
    }
}