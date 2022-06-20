package com.example.walletconnectsdk

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kuknos.walletconnect.WalletConnect
import com.kuknos.walletconnect.WalletConnectCallback
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity(),WalletConnectCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        jsLoader.getSettings().setJavaScriptEnabled(true)
        var walletConnect = WalletConnect.init(this)
        jsLoader.addJavascriptInterface(walletConnect.getJsInterface(this,"GCGKKROD333C2FYXFZGLEGGEXJPQES635PSQY64FMRXX7L73RROENSTV")!!, "AndroidFunction")
        jsLoader.loadUrl("https://test.dev.kuknos.ir/")


        Handler().postDelayed(Runnable {
            jsLoader.loadUrl("javascript:window.wallet = true")
        },5000)

    }

    //---- getter and setter--------
    class MyJavaScriptInterface internal constructor(var mContext: Context) {
        @JavascriptInterface
        fun connectRequest(data: String?) {
            Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show()

        }
    }

    override fun onRequestListener(projectId: String, actionType: String, data: Any) {

    }

    override fun onConnected() {

    }

    override fun onDisconnected() {

    }
}