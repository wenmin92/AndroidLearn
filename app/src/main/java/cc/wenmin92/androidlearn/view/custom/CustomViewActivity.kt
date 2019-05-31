package cc.wenmin92.androidlearn.view.custom

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cc.wenmin92.androidlearn.R
import timber.log.Timber
import android.R.attr.top
import android.graphics.Rect


class CustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        val viewCustom = findViewById<View>(R.id.view_custom)
        viewCustom.post {
            Timber.d("customView: [W${viewCustom.width}:H${viewCustom.height}]")
        }

        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        val statusBarHeight = rect.top
    }
}
