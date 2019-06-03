package cc.wenmin92.androidlearn

import android.app.Application
import android.util.Log

import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogTool()
        initLeakCanary()
    }

    /**
     * 内存泄漏检查
     */
    private fun initLeakCanary() {
        // if (LeakCanary.isInAnalyzerProcess(this)) {
        //     return;
        // }
        // LeakCanary.install(this);
    }

    /**
     * 设置log工具
     */
    private fun initLogTool() {
        if (BuildConfig.DEBUG)
            Timber.plant(ThreadAwareDebugTree())
        else
            Timber.plant(ReleaseTree())
    }

    open inner class ThreadAwareDebugTree : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            var tagUsed = tag
            if (tag != null) {
                val threadName = Thread.currentThread().name
                tagUsed = "<$threadName> $tag"
            }
            super.log(priority, tagUsed, message, t)
        }

        override fun createStackElementTag(element: StackTraceElement): String? {
            return super.createStackElementTag(element) + "(Line " + element.lineNumber + ")"  //日志显示行号
        }
    }

    inner class ReleaseTree : ThreadAwareDebugTree() {
        override fun isLoggable(tag: String?, priority: Int): Boolean {
            return priority != Log.VERBOSE && priority != Log.DEBUG && priority != Log.INFO
        }

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (!isLoggable(tag, priority)) return
            super.log(priority, tag, message, t)
        }
    }
}
