## Install
  ``` kotlin
    maven { url 'https://jitpack.io' }

    implementation 'com.github.KuknosCo:WalletConnet-kotlin:1.2.1'
  ```

## Initiate

``` kotlin
  walletConnect = WalletConnect.init(object:WalletConnectCallback{
            override fun onRequestListener(projectId: String, actionType: String, data: Any) {
                
            }
            override fun onConnected() {
                
            }
            override fun onDisconnected() {
                
            }
        })
  ```

  ## Connect
  ``` kotlin
    walletConnect?.connect(qrData,publicKey,context)
  ```
## Send
  ``` kotlin
    walletConnect?.send(projectId,jsonData,actionType,message,true,context)
  ```

  ## Disconnect
  ``` kotlin
    walletConnect?.disconnect()
  ```

## Deeplink
link 'https://wc-deeplink.kuknos.ir/{DATA}'
  ``` kotlin
    walletConnect?.setDeeplinkData(data,publicKey,context)
  ```
## ActionsTypes
``` kotlin
    val ACTION_GET_ACCOUNT = "account-publickey"
    val ACTION_SIGN_DATA = "sign-data"
    val ACTION_SIGN_XDR = "sign-xdr"
    val ACTION_CHANGE_TRUST = "change-trust"
    val ACTION_CREATE_ACCOUNT = "create-account"
    val ACTION_CURVE_DECRYPT = "curve-decrypt"
    val ACTION_PAYMENT = "payment"
    val ACTION_BUY_TOKEN = "buy-token"
    val ACTION_PING = "ping"
    val ACTION_DISCONNECT = "disconnect"
    val ACTION_WALLET_CONNECT_REQUEST = "wallet-connect-request"
    val DISCONNECT_SOCKET = "disconnect"
    val ACTION_SIGN_CMS = "pki-sign-cms"
    val ACTION_SIGN_pdf = "pki-sign-pdf"
  ```