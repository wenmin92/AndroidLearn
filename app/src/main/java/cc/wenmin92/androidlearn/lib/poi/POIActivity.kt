package cc.wenmin92.androidlearn.lib.poi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cc.wenmin92.androidlearn.R
import org.apache.poi.hwpf.HWPFDocument
import java.io.File

class POIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi)


        val file = File(cacheDir, "text.doc")
        assets.open("empty.doc").use { input ->
            file.outputStream().use { output ->
                val doc = HWPFDocument(input)
                doc.range.insertAfter("Hello, world!")
                doc.write(output)
            }
        }
    }
}
