package cc.wenmin92.androidlearn.lib.poi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cc.wenmin92.androidlearn.R
import com.deepoove.poi.XWPFTemplate
import com.lowagie.text.Font
import com.lowagie.text.pdf.BaseFont
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions
import kotlinx.android.synthetic.main.activity_poi.*
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File


class POIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi)

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl")
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl")
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl")

        // testHWPF()
        // btn_gen_word.setOnClickListener { testXWPF() }
        btn_gen_pdf.setOnClickListener { testDocx2Pdf() }
    }

    private fun testHWPF() {
        val file = File(cacheDir, "text.doc")
        // assets.open("empty.doc").use { input ->
        //     file.outputStream().use { output ->
        //         val doc = HWPFDocument(input)
        //         doc.range.insertAfter("Hello, world!")
        //         doc.write(output)
        //     }
        // }
    }

    private fun testXWPF() {
        val file = File(cacheDir, "text.docx")
        assets.open("empty.docx").use { input ->
            file.outputStream().use { output ->
                val template: XWPFTemplate = XWPFTemplate.compile(input).render(mapOf(Pair("title", "Poi-tl 模板引擎")))
                template.write(output)
                // template.close()
            }
        }
    }

    private fun testDocx2Pdf() {
        val fontFile = File(cacheDir, "FZHTJW.TTF")
        if (!fontFile.exists()) {
            assets.open("FZHTJW.TTF").use { input ->
                fontFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
        val baseFont = BaseFont.createFont(fontFile.absolutePath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)
        val file = File(cacheDir, "temp.pdf")
        assets.open("temp.docx").use { input ->
            file.outputStream().use { output ->
                val options = PdfOptions.getDefault().apply {
                    fontProvider { familyName, encoding, size, style, color ->
                        Font(baseFont, size, style, color)
                    }
                }
                PdfConverter.getInstance().convert(XWPFDocument(input), output, options)
            }
        }
    }
}
