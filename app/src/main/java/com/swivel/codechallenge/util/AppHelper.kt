package com.swivel.codechallenge.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class AppHelper  {

    companion object {
        fun isInternetAvailable(context: Context): Boolean{
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnected == true
        }
    }
}
