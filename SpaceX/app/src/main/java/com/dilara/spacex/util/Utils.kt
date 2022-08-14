package com.dilara.spacex.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

object Utils {
    fun showToast(context:Context,message:String)=Toast.makeText(context,message,Toast.LENGTH_LONG).show()

}