@file:Suppress("unused")

package cc.wenmin92.androidlearn.jetpack.lifecycle

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import cc.wenmin92.androidlearn.R
import timber.log.Timber

class LifecycleActivity : AppCompatActivity() {
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate() begin, current=${lifecycle.currentState}")
        handler.post { Timber.d("onCreate() begin, handler.post") }
        super.onCreate(savedInstanceState)
        handler.post { Timber.d("onCreate() super.onCreate(), handler.post") }
        setContentView(R.layout.activity_lifecycle)
        handler.post { Timber.d("onCreate() setContentView(), handler.post") }
        CustomFun(lifecycle)

        Timber.d("onCreate() end, current=${lifecycle.currentState}")
    }

    override fun onStart() {
        Timber.d("onStart() begin, current=${lifecycle.currentState}")
        super.onStart()
        Timber.d("onStart() end, current=${lifecycle.currentState}")
    }

    override fun onResume() {
        Timber.d("onResume() begin, current=${lifecycle.currentState}")
        super.onResume()
        Timber.d("onResume() end, current=${lifecycle.currentState}")
    }

    override fun onRestart() {
        Timber.d("onRestart() begin, current=${lifecycle.currentState}")
        super.onRestart()
        Timber.d("onRestart() end, current=${lifecycle.currentState}")
    }

    override fun onPause() {
        Timber.d("onPause() begin, current=${lifecycle.currentState}")
        super.onPause()
        Timber.d("onPause() end, current=${lifecycle.currentState}")
    }

    override fun onStop() {
        Timber.d("onStop() begin, current=${lifecycle.currentState}")
        super.onStop()
        Timber.d("onStop() end, current=${lifecycle.currentState}")
    }

    override fun onDestroy() {
        Timber.d("onDestroy() begin, current=${lifecycle.currentState}")
        super.onDestroy()
        Timber.d("onDestroy() end, current=${lifecycle.currentState}")
    }

}

class CustomFun(private val lifecycle: Lifecycle) : LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Event.ON_CREATE)
    fun testA() {
        Timber.d("Event.ON_CREATE, current=${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Event.ON_START)
    fun testB() {
        Timber.d("Event.ON_START, current=${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Event.ON_RESUME)
    fun testC() {
        Timber.d("Event.ON_RESUME, current=${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Event.ON_PAUSE)
    fun testD() {
        Timber.d("Event.ON_PAUSE, current=${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Event.ON_STOP)
    fun testE() {
        Timber.d("Event.ON_STOP, current=${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    fun testF() {
        Timber.d("Event.ON_DESTROY, current=${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Event.ON_ANY)
    fun testG() {
        Timber.d("Event.ON_ANY, current=${lifecycle.currentState}")
    }
}
