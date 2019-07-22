package cc.wenmin92.androidlearn.binder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import cc.wenmin92.androidlearn.IMyAidlInterface

class TestService : Service() {
    private val binder: MyAidlTest = MyAidlTest()

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }
}

class MyAidlTest : IMyAidlInterface.Stub() {
    override fun testVoidAidl() {
    }

    override fun testStringAidl(): String {
        return "test"
    }
}
