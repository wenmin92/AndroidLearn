package cc.wenmin92.androidlearn.net.wifi

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cc.wenmin92.androidlearn.R
import timber.log.Timber

const val TAG = "[WiFiTest]"

class WiFiTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wi_fi_test)

        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        manager.addDefaultNetworkActiveListener { Timber.d("$TAG DefaultNetworkActiveListener#onNetworkActive()") }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_action1 -> startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
            R.id.btn_action2 -> startActivity(Intent(WifiManager.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE))
            R.id.btn_action3 -> startActivity(Intent(WifiManager.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE))
            else -> {
            }
        }
    }
}
