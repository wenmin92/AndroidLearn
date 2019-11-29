package cc.wenmin92.androidlearn.net

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.scale
import cc.wenmin92.androidlearn.R
import kotlinx.android.synthetic.main.activity_net_test.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "[NetTest]"

class NetTestActivity : AppCompatActivity() {

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_test)

        btn_clear.setOnClickListener { tv_content.text = "" }

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // active network
        log("$TAG activeNetworkInfo: ${cm.activeNetworkInfo}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            log("$TAG activeNetwork: ${cm.activeNetwork}")
        }
        cm.addDefaultNetworkActiveListener { log("onNetworkActive") }
        log("$TAG isDefaultNetworkActive: ${cm.isDefaultNetworkActive}")

        log("$TAG allNetworks: ${cm.allNetworks.joinToString()}")
        log("$TAG allNetworkInfo: ${cm.allNetworkInfo.joinToString()}")

        // val request = NetworkRequest.Builder()
        //         .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        //         .build()
        cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
                log(format("CALLBACK", "onLinkPropertiesChanged", Content("network", network), Content("linkProperties", linkProperties), color = COLOR_RED))
            }

            override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
                log(format("CALLBACK", "onCapabilitiesChanged", Content("network", network), Content("networkCapabilities", networkCapabilities), Content("wifiInfo", getWifiInfo())))
            }

            override fun onUnavailable() {
                log(format("CALLBACK", "onUnavailable", color = COLOR_RED))
            }

            override fun onLost(network: Network?) {
                log(format("CALLBACK", "onLost", Content("network", network), color = COLOR_RED))
            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                log(format("CALLBACK", "onLosing", Content("network", network), Content("maxMsToLive", maxMsToLive), color = COLOR_RED))
            }

            override fun onAvailable(network: Network?) {
                log(format("CALLBACK", "onAvailable", Content("network", network), color = COLOR_RED))
            }

            override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                log(format("CALLBACK", "onBlockedStatusChanged", Content("network", network), Content("blocked", blocked), color = COLOR_RED))
            }
        })

        val filter = IntentFilter().apply {
            addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
            addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
            addAction(WifiManager.RSSI_CHANGED_ACTION)
            addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        }
        registerReceiver(object : BroadcastReceiver() {
            @SuppressLint("NewApi")
            override fun onReceive(context: Context?, intent: Intent?) {
                val color = when (intent?.action) {
                    WifiManager.NETWORK_STATE_CHANGED_ACTION -> COLOR_RED
                    WifiManager.WIFI_STATE_CHANGED_ACTION -> COLOR_RED
                    WifiManager.RSSI_CHANGED_ACTION -> COLOR_GREEN
                    ConnectivityManager.CONNECTIVITY_ACTION -> COLOR_RED
                    else -> COLOR_RED
                }
                var contents = arrayOf<Content>()
                when (intent?.action) {
                    WifiManager.NETWORK_STATE_CHANGED_ACTION -> {
                        contents = contents.plus(Content("extra_networkInfo", intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)))
                        contents = contents.plus(Content("networkCapabilities", cm.getNetworkCapabilities(cm.activeNetwork)))
                        contents = contents.plus(Content("linkProperties", cm.getLinkProperties(cm.activeNetwork)))
                    }
                    WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                        contents = contents.plus(Content("extra_preWifiState", getWifiState(intent.getIntExtra(WifiManager.EXTRA_PREVIOUS_WIFI_STATE, 99))))
                        contents = contents.plus(Content("extra_curWifiState", getWifiState(intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 99))))
                    }
                    WifiManager.RSSI_CHANGED_ACTION -> {
                        contents = contents.plus(Content("extra_newRSSI", intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -1)))
                    }
                    WifiManager.SUPPLICANT_STATE_CHANGED_ACTION -> {
                        contents = contents.plus(Content("extra_newState", intent.getParcelableExtra<SupplicantState>(WifiManager.EXTRA_NEW_STATE)))
                        contents = contents.plus(Content("extra_supplicant_error", intent.getParcelableExtra<SupplicantState>(WifiManager.EXTRA_SUPPLICANT_ERROR)))
                    }
                    ConnectivityManager.CONNECTIVITY_ACTION -> {
                        contents = contents.plus(Content("networkInfo", cm.activeNetworkInfo))
                        contents = contents.plus(Content("networkCapabilities", cm.getNetworkCapabilities(cm.activeNetwork)))
                        contents = contents.plus(Content("linkProperties", cm.getLinkProperties(cm.activeNetwork)))
                    }
                }
                contents = contents.plus(Content("wifiInfo", getWifiInfo()))
                log(format("BROADCAST", intent?.action ?: "null", *contents, color = color))
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

    private fun getWifiState(state: Int): String {
        return when (state) {
            WifiManager.WIFI_STATE_DISABLED -> "WIFI_STATE_DISABLED"
            WifiManager.WIFI_STATE_DISABLING -> "WIFI_STATE_DISABLING"
            WifiManager.WIFI_STATE_ENABLED -> "WIFI_STATE_ENABLED"
            WifiManager.WIFI_STATE_ENABLING -> "WIFI_STATE_ENABLING"
            WifiManager.WIFI_STATE_UNKNOWN -> "WIFI_STATE_UNKNOWN"
            else -> "Unkown"
        }
    }

    private fun format(type: String, title: String, vararg contents: Content, color: Int = COLOR_GREEN): CharSequence {
        return buildSpannedString {
            color(color) {
                bold { append(type) }
                append("  $title\n")
            }
            scale(0.6f) {
                color(COLOR_TIME) {
                    append("${dateFormat.format(Date())}\n")
                }
            }
            contents.forEach {
                bold { append("${it.title}: ") }
                color(0xFFD0D0D0.toInt()) {
                    if (it.content != null)
                        append(it.content.toString())
                }
                append("\n")
            }
            append("\n")
        }
    }

    private fun log(msg: CharSequence) {
        Timber.d(msg.toString())
        runOnUiThread {
            tv_content.append(msg)
            tv_content.append("\n\n")
        }
    }
}

private data class Content(val title: String, val content: Any?)

private const val COLOR_RED = 0xFFF46A54.toInt()
private const val COLOR_GREEN = 0xFF078107.toInt()
private const val COLOR_TIME = 0xFF744B13.toInt()

private val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.US)
