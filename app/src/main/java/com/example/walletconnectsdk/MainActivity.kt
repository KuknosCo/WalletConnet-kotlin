package com.example.walletconnectsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.kuknos.walletconnect.WalletConnect
import com.kuknos.walletconnect.WalletConnectCallback
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity(), WalletConnectCallback{
    private lateinit var codeScanner: CodeScanner
    private var walletConnect : WalletConnect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

        codeScanner = CodeScanner(this,scannerView)
        codeScanner.decodeCallback = DecodeCallback {

            walletConnect = WalletConnect.init(this)
            walletConnect?.connect(it.text,"GCGKKROD333C2FYXFZGLEGGEXJPQES635PSQY64FMRXX7L73RROENSTV",this)

            /*walletConnect?.send()
            walletConnect?.disconnect()*/

        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }


    override fun onRequestListener(projectId: String, actionType: String, data: Any) {
        var dataString = data as String
        Log.i("MizbaneSDK","type = "+actionType+"   data = "+dataString)
        //walletConnect?.send(projectId,null,actionType,"",true,this)
    }

    override fun onConnected() {

    }

    override fun onDisconnected() {

    }

}