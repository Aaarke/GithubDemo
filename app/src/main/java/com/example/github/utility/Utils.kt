package com.example.github.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


@Suppress("DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Utils {
    companion object {
        fun internetCheck(context: Context): Boolean {
            val net: Boolean
            try {
                val conMgr =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                net =
                    conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED || conMgr.getNetworkInfo(
                        ConnectivityManager.TYPE_MOBILE
                    ).state == NetworkInfo.State.CONNECTED
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }

            return net

        }

    }
}