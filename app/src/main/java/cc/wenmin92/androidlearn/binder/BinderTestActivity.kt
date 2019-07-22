package cc.wenmin92.androidlearn.binder

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import cc.wenmin92.androidlearn.IMyAidlInterface
import cc.wenmin92.androidlearn.R
import timber.log.Timber

const val TAG: String = "[binderTest]"

class BinderTestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder_test)

        val intent = Intent(this, TestService::class.java)

        Timber.d("$TAG bindService")
        bindService(intent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Timber.d("$TAG onServiceConnected success")
                val binder = IMyAidlInterface.Stub.asInterface(service)
                val result = binder.testStringAidl()
                Timber.d("$TAG onServiceConnected, result=$result")
            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }

        }, 0)
    }
}
