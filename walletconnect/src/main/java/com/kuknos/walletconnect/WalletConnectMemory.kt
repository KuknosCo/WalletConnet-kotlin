package com.kuknos.walletconnect

import android.content.Context
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences
import android.util.Log


object WalletConnectMemory {
    val MY_PREFS_NAME = "MY_PREFS_NAME"
    fun save(key :String ,value :String,context: Context){
        val editor: SharedPreferences.Editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()
        editor.putString(key, value)
        editor.apply()
        //Log.i("MyLog","save")
    }

    fun load(key :String,context: Context):String{
        val prefs: SharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
        val value = prefs.getString(key,"")?:""
        //Log.i("MyLog","load : "+value)
        return value
    }
}