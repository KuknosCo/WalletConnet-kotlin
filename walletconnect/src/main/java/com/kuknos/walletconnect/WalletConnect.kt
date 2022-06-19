package com.kuknos.walletconnect

import android.content.Context
import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kuknos.walletconnect.cryptography.AESEncryption
import com.kuknos.walletconnect.walletconnectmodels.*
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.math.BigInteger
import java.net.URI
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

object WalletConnect {
    private var pId :String? = null
    private var socket: Socket? = null
    private var listener: WalletConnectCallback? = null
    fun init(listener : WalletConnectCallback):WalletConnect{
        this.listener = listener
        return this
    }

    fun connect(data :String,accountId:String,context: Context){
        var split = data.split("//")
        var content = String(Base64.decode(split[1],Base64.DEFAULT))
        Log.i("MyLog",content)

        var json = JSONObject(content)
        var key = json.getString("key")
        var project_id = json.getString("project_id")
        var address = json.getString("relayServer")
        Log.i("MyLog","address "+address)

        val options = IO.Options.builder().setAuth(Collections.singletonMap("project_id", accountId)).build()
        val uri = URI.create(address)
        socket = IO.socket(uri, options)
        socket?.connect()

        socket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.i("MyLog","connected")
            WalletConnectMemory.save(project_id,key,context)
            startListen(context)
            Log.i("MyLog","pId : "+project_id)
            pId = project_id
            send(project_id,createGetAccountJson(accountId), WalletConnectConstants.ACTION_GET_ACCOUNT,"",true,context)
            sendConnectRequest(project_id,accountId,context)

            listener?.let {
                it.onConnected()
            }
        })
        socket?.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            Log.i("MyLog","disconnect")
            pId = null
            listener?.let {
                it.onDisconnected()
            }
        })

    }

    fun disconnect(){
        socket?.let {
            it.disconnect()
        }
    }

    private fun startListen(context: Context){
        Log.i("MyLog","A")
        socket?.let {
            Log.i("MyLog","B")
            it.on("receive_data", Emitter.Listener {
                val json = it[0] as JSONObject

                manageRequests(json,context)
            })
        }
    }

    private fun sendConnectRequest(project_id: String, accountId: String,context: Context) {


        val obj = JsonObject()
        obj.addProperty("type","wallet_info")
        obj.addProperty("wallet_info",encrypt(project_id, createAccountJson(accountId).toString(),context))
        obj.addProperty("meta","")
        val model = SocketConnectModel(obj, project_id)
        val gson = Gson()
        val toJson = gson.toJson(model)
        val json = JSONObject(toJson)
        Log.i("sendmodel","0 : "+json)
        socket?.emit("send_data", json)
    }

    fun send(project_id: String, data: JSONObject?, type: String,message: String,status:Boolean,context: Context){
        var status = if (status) "submit" else "reject"
        val responseModel = SocketResponseModel(status, message, type, encrypt(project_id,data.toString(),context))
        val model = SocketSubmitModel(responseModel, project_id)
        val gson = Gson()
        val toJson = gson.toJson(model)
        val json = JSONObject(toJson)
        Log.i("sendmodel","send : "+json)
        socket?.emit("send_data", json)
    }

    private fun encrypt(project_id: String,data: String,context: Context):String{


        var key = getMD5EncryptedString(WalletConnectMemory.load(project_id,context))
        Log.i("sendmodel","final key : "+key)
        val encrypt = AESEncryption(key).encrypt(data)
        Log.i("sendmodel","encrypt : "+encrypt)
        var replace = encrypt.trim().replace("\n","")
        return replace

    }

    private fun decrypt(context: Context,data: String,project_id: String):String{
        var key = getMD5EncryptedString(WalletConnectMemory.load(project_id,context))
        Log.i("hjgklk","final key : "+key)
        var decrypt = AESEncryption(key).decrypt(data)
        Log.i("hjgklk","decrypt : "+decrypt)
        return decrypt
    }

    private fun createGetAccountJson(public : String):JSONObject{
        val json = JSONObject()
        json.put("public",public)
        return json
    }

    private fun createAccountJson(public : String):JSONObject{
        val json = JSONObject()
        json.put("project_id",public)
        return json
    }

    private fun getMD5EncryptedString(encTarget: String): String? {
        var mdEnc: MessageDigest? = null
        try {
            mdEnc = MessageDigest.getInstance("MD5")
//            val digest = mdEnc.digest()
//            Log.i("MyError","digst : "+digest)
        } catch (e: NoSuchAlgorithmException) {
            println("Exception while encrypting to md5")
            e.printStackTrace()
        } // Encryption algorithm
        mdEnc!!.update(encTarget.toByteArray(), 0, encTarget.length)
        var md5: String = BigInteger(1, mdEnc.digest()).toString(16)
        while (md5.length < 32) {
            md5 = "0$md5"
        }
        return md5
    }

    private fun manageRequests(jsonRequest : JSONObject,context: Context){
        val gson = Gson()
        val socketRequestModel = gson.fromJson(jsonRequest.toString(), SocketRequestModel::class.java)
        if (!socketRequestModel.type.equals(WalletConnectConstants.ACTION_PING))
            Log.i("MyLog","receive this : "+jsonRequest)

        context?.let { cntx ->
            when(socketRequestModel.type){
                WalletConnectConstants.ACTION_PING ->{
                    if (socketRequestModel.client?.project_id?.equals(pId) == true){
                        send(socketRequestModel.client?.project_id?:"", JSONObject(), WalletConnectConstants.ACTION_PING,"",true,cntx)
                    }else{}
                }
                WalletConnectConstants.ACTION_DISCONNECT ->{
                    disconnect()
                }
                else -> {
                    listener?.let {
                        var data = decrypt(context,socketRequestModel.data as String,socketRequestModel.client?.project_id?:"")
                        it.onRequestListener(socketRequestModel.client?.project_id?:"",socketRequestModel.type?:"",data)
                    }
                }
            }
        }

    }
}