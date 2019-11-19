package com.appdot.io.wikipediareader.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

    }

    private fun registerConnectionReceiver(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            val receiver = MyBroadcastReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            //regist
        }
    }

}
