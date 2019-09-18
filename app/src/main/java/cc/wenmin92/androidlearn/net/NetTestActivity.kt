package cc.wenmin92.androidlearn.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
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
        cm.addDefaultNetworkActiveListener {
            log("onNetworkActive")
        }
        Timber.d("$TAG isDefaultNetworkActive: ${cm.isDefaultNetworkActive}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
                    log("onCapabilitiesChanged: network[$network], networkCapabilities[$networkCapabilities]")
                }

                override fun onLost(network: Network?) {
                    log("onLost")
                }

                override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
                    log("onLinkPropertiesChanged")
                }

                override fun onUnavailable() {
                    log("onLinkPropertiesChanged")
                }

                override fun onLosing(network: Network?, maxMsToLive: Int) {
                    log("onLosing")
                }

                override fun onAvailable(network: Network?) {
                    log("onLosing")
                }
            })
        }
    }

    fun log(msg: String) {
        Timber.d("$TAG $msg")
        runOnUiThread { tv_content.append("$msg\n\n") }
    }
}
