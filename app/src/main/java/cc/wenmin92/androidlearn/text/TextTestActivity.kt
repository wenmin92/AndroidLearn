package cc.wenmin92.androidlearn.text

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cc.wenmin92.androidlearn.R
import timber.log.Timber


class TextTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_test)

        val etText = findViewById<EditText>(R.id.et_text)
        // etText.filters = arrayOf(InputFilter.LengthFilter(4))

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Timber.d(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Timber.d("onTextChanged, s=$s")
            }

        }

        val spannable = SpannableString("This is a multiline text. LineBackgroundSpan is applied here. This is a multiline text.")
        spannable.setSpan(watcher, 1, 5, Spanned.SPAN_MARK_POINT)

        etText.setText(spannable)

        val tvText = findViewById<TextView>(R.id.tv_text)
        val text = "This is a multiline text. LineBackgroundSpan is applied here. This is a multiline text."
        val string = SpannableString(text)
        // string.setSpan(LineBackgroundSpan.Standard(Color.YELLOW), 26, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvText.text = string;
    }
}
