package cc.wenmin92.androidlearn.text

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.EditText
import cc.wenmin92.androidlearn.R

class TextTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_test)

        val etText = findViewById<EditText>(R.id.et_text)
        etText.filters = arrayOf(InputFilter.LengthFilter(4))
    }
}
