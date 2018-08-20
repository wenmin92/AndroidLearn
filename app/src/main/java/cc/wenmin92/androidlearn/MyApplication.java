package cc.wenmin92.androidlearn;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLogTool();
        initLeakCanary();
    }

    /**
     * 内存泄漏检查
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * 设置log工具
     */
    private void initLogTool() {
        if (BuildConfig.DEBUG)
            Timber.plant(new ThreadAwareDebugTree());
        else
            Timber.plant(new ReleaseTree());
    }

    public class ThreadAwareDebugTree extends Timber.DebugTree {
        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            if (tag != null) {
                String threadName = Thread.currentThread().getName();
                tag = "<" + threadName + "> " + tag;
            }
            super.log(priority, tag, message, t);
        }

        @Override
        protected String createStackElementTag(@NonNull StackTraceElement element) {
            return super.createStackElementTag(element) + "(Line " + element.getLineNumber() + ")";  //日志显示行号
        }
    }

    public class ReleaseTree extends ThreadAwareDebugTree {
        @Override
        protected boolean isLoggable(String tag, int priority) {
            return priority != Log.VERBOSE && priority != Log.DEBUG && priority != Log.INFO;
        }

        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            if (!isLoggable(tag, priority)) return;
            super.log(priority, tag, message, t);
        }
    }
}
