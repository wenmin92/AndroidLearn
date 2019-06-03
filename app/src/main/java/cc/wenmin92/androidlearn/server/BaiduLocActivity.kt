package cc.wenmin92.androidlearn.server

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cc.wenmin92.androidlearn.R
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import timber.log.Timber
import java.text.NumberFormat


class BaiduLocActivity : AppCompatActivity() {

    private var mLocationClient: LocationClient? = null
    private val myListener = MyLocationListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baidu_loc)

        mLocationClient = LocationClient(applicationContext)
        mLocationClient?.registerLocationListener(myListener)

        val option = LocationClientOption()
        // option.locationMode = LocationMode.Hight_Accuracy
        // option.setCoorType("bd09ll")
        // option.setScanSpan(0)
        // option.isOpenGps = true
        // option.isLocationNotify = true
        // option.setIgnoreKillProcess(false)
        // option.SetIgnoreCacheException(false)
        // option.setWifiCacheTimeOut(5 * 60 * 1000)
        // option.setEnableSimulateGps(false)
        option.setIsNeedAddress(true)
        mLocationClient?.locOption = option

        mLocationClient?.start()

        val tvText = findViewById<TextView>(R.id.tv_text)
        tvText.setOnClickListener {
            if (mLocationClient != null && mLocationClient!!.isStarted) {
                Timber.d("requestLocation: ${mLocationClient?.requestLocation()}")
            }
        }
    }
}

class MyLocationListener : BDAbstractLocationListener() {
    @Suppress("UNUSED_VARIABLE")
    override fun onReceiveLocation(location: BDLocation) {

        val latitude = location.latitude    // 获取纬度信息
        val longitude = location.longitude  // 获取经度信息
        val radius = location.radius        // 获取定位精度，默认值为0.0f
        val coorType = location.coorType
        val errorCode = location.locType

        val format = NumberFormat.getNumberInstance()
        Timber.d("latitude: ${format.format(latitude)}, longitude: ${format.format(longitude)}, city: ${location.city}, locType: ${location.locType}")
    }

    /**
     * 收不到回调，不知道什么原因？
     */
    override fun onLocDiagnosticMessage(locType: Int, diagnosticType: Int, diagnosticMessage: String?) {
        Timber.d("DiagnosticMessage: locType: $locType, diagnosticType: $diagnosticType, diagnosticMessage: $diagnosticMessage")
    }
}
