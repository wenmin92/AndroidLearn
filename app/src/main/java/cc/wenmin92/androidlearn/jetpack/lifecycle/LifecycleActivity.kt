@file:Suppress("unused")

package cc.wenmin92.androidlearn.jetpack.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import cc.wenmin92.androidlearn.R
import timber.log.Timber

class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        CustomFun(lifecycle)
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
