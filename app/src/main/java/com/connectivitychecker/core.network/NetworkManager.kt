package com.connectivitychecker.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.net.NetworkInterface

class NetworkManager(private val context: Context) {

    /*
    * return true if the mobile is connected via VPN (in this case Gnirehtet)
    */
    fun isConnectedUsingGnirehtet(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val isVpnConnected = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        }
        else @Suppress("DEPRECATION") {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo ?: return false
            activeNetworkInfo.type == ConnectivityManager.TYPE_VPN
        }
        println("isConnectedUsingGnirehtet = $isVpnConnected")
        return isVpnConnected
    }

    /*
    * "tun" is typically used to denote TUN/TAP interfaces, which are used by VPNs or similar tunneling solutions.
    * gnirehtet uses a TUN interface for its functionality, so detecting "tun" in an interface name implies that the gnirehtet VPN is active.
    */
    fun isGnirehtetInterfaceActive(): Boolean {
        val interfaces = NetworkInterface.getNetworkInterfaces().toList()
        val result =  interfaces.any { it.name.contains("tun", ignoreCase = true) }
        println("isGnirehtetInterfaceActive = $result")
        return result
    }
}