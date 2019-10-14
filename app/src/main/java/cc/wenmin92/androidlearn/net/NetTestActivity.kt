package cc.wenmin92.androidlearn.net

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cc.wenmin92.androidlearn.R
import kotlinx.android.synthetic.main.activity_net_test.*
import timber.log.Timber

const val TAG = "[NetTest]"

class NetTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_test)

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // active network
        log("$TAG activeNetworkInfo: ${cm.activeNetworkInfo}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            log("$TAG activeNetwork: ${cm.activeNetwork}")
        }
        cm.addDefaultNetworkActiveListener { log("onNetworkActive") }
        Timber.d("$TAG isDefaultNetworkActive: ${cm.isDefaultNetworkActive}")

        log("$TAG allNetworks: ${cm.allNetworks.joinToString()}")
        log("$TAG allNetworkInfo: ${cm.allNetworkInfo.joinToString()}")


        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //     cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
        //         override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
        //             log("onCapabilitiesChanged: network[$network], networkCapabilities[$networkCapabilities]")
        //         }
        //
        //         override fun onLost(network: Network?) {
        //             log("onLost: network[$network]")
        //         }
        //
        //         override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
        //             log("onLinkPropertiesChanged: network[$network], linkProperties[$linkProperties]")
        //         }
        //
        //         override fun onUnavailable() {
        //             log("onLinkPropertiesChanged")
        //         }
        //
        //         override fun onLosing(network: Network?, maxMsToLive: Int) {
        //             log("onLosing: network[$network], maxMsToLive[$maxMsToLive]")
        //         }
        //
        //         override fun onAvailable(network: Network?) {
        //             log("onLosing: network[$network]")
        //         }
        //     })
        // }
        val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
        cm.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
                log("onCapabilitiesChanged: network[$network], networkCapabilities[$networkCapabilities]\nwifiInfo: ${getWifiInfo()}")

            }

            override fun onLost(network: Network?) {
                log("onLost: network[$network]")
            }

            override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
                log("onLinkPropertiesChanged: network[$network], linkProperties[$linkProperties]")
            }

            override fun onUnavailable() {
                log("onLinkPropertiesChanged")
            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                log("onLosing: network[$network], maxMsToLive[$maxMsToLive]")
            }

            override fun onAvailable(network: Network?) {
                log("onLosing: network[$network]")
            }
        })

        val filter = IntentFilter().apply {
            addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
            addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
            addAction(WifiManager.RSSI_CHANGED_ACTION)
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        }
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                log("Broadcast: receive ${intent?.action ?: "null"}\nwifiInfo: ${getWifiInfo()}")
            }
        }, filter)
    }

    var lastBSSID = ""

    fun getWifiInfo(): String {
        val wm = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var bssidChange = ""
        val newBssid = wm.connectionInfo.bssid
        if (lastBSSID != newBssid && newBssid != null) {
            bssidChange = "\n****************************************\n$lastBSSID --> $newBssid"
            lastBSSID = newBssid
        }
        return wm.connectionInfo.toString() + bssidChange
    }

    fun log(msg: String) {
        Timber.d("$TAG $msg")
        runOnUiThread { tv_content.append("$msg\n\n") }
    }
}
