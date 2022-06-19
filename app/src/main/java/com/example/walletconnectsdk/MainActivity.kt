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
        walletConnect = WalletConnect.init(this)
        if (intent.data != null) {
            try {
                val uri = intent?.data
                var data = uri?.getPath()?:""
                walletConnect?.setDeeplinkData(data,"GCGKKROD333C2FYXFZGLEGGEXJPQES635PSQY64FMRXX7L73RROENSTV",this)
                /*val spPath: List<String> = uri?.getPath()?.split("/")!!
                spPath.forEachIndexed { index, s ->
                    Log.i("MizbaneSDK","index : "+index+"    text : "+s )
                }*/
            } catch (e: Exception) {}
        }



        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

        codeScanner = CodeScanner(this,scannerView)
        codeScanner.decodeCallback = DecodeCallback {


            walletConnect?.connect(it.text,"GCGKKROD333C2FYXFZGLEGGEXJPQES635PSQY64FMRXX7L73RROENSTV",this)

            /*walletConnect?.send()
            walletConnect?.disconnect()*/

        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }


        /*walletConnect = WalletConnect.init(object:WalletConnectCallback{
            override fun onRequestListener(projectId: String, actionType: String, data: Any) {

            }
            override fun onConnected() {

            }
            override fun onDisconnected() {

            }
        })*/

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
        walletConnect?.send(projectId,null,actionType,"",false,this)
    }

    override fun onConnected() {

    }

    override fun onDisconnected() {

    }

}