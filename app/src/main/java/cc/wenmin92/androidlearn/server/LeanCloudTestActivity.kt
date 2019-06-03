package cc.wenmin92.androidlearn.server

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cc.wenmin92.androidlearn.R
import cn.leancloud.AVObject

class LeanCloudTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lean_cloud_test)
        AVObject("TestTable").apply {
            put("name", "abc")
            put("description", "abc is abc")
            saveInBackground().subscribe { Toast.makeText(this@LeanCloudTestActivity, "save success", Toast.LENGTH_LONG).show() }
        }

    }

}
