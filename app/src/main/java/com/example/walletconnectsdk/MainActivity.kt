package com.example.walletconnectsdk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.kuknos.walletconnect.WalletConnect
import com.kuknos.walletconnect.WalletConnectCallback

class MainActivity : AppCompatActivity(), WalletConnectCallback{
    private lateinit var codeScanner: CodeScanner
    private var walletConnect : WalletConnect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        walletConnect = WalletConnect.init(this)
        Log.i("MizbaneSDK","C" )
        if (intent.data != null) {
            Log.i("MizbaneSDK","D" )
            try {
                val uri = intent?.data
                var data = uri?.getPath()?:""
                Log.i("MizbaneSDK","Data "+data )
                walletConnect?.setDeeplinkData(data,"GCGKKROD333C2FYXFZGLEGGEXJPQES635PSQY64FMRXX7L73RROENSTV",this)
                /*val spPath: List<String> = uri?.getPath()?.split("/")!!
                spPath.forEachIndexed { index, s ->
                    Log.i("MizbaneSDK","index : "+index+"    text : "+s )
                }*/
            } catch (e: Exception) {
                Log.i("MizbaneSDK","E "+e.message )
            }
        }



        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

        codeScanner = CodeScanner(this,scannerView)
        codeScanner.decodeCallback = DecodeCallback {


            walletConnect?.connect(it.text,"GCGKKROD333C2FYXFZGLEGGEXJPQES635PSQY64FMRXX7L73RROENSTV",this,null)

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

//        runJs()

    }




    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }


    override fun onRequestListener(projectId: String, actionType: String, data: Any,network:String?) {
        var dataString = data as String
        try {
            runOnUiThread {
                var m = "project id = "+projectId +"\n"+"\n"+"action type = "+actionType+"\n"+"\n"+"data = "+dataString
                Toast.makeText(this,m,Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            Log.i("MizbaneSDK","e = "+e.message)
        }
        Log.i("MizbaneSDK","type = "+actionType+"   data = "+dataString)
        walletConnect?.send(projectId,null,actionType,"",false,this)


    }

    override fun onConnected() {

    }

    override fun onDisconnected() {

    }

    private fun runJs() {
        startActivity(Intent(this,WebViewActivity::class.java))
    }
}