package cc.wenmin92.androidlearn.lib.poi

import org.apache.poi.hwpf.HWPFDocument
import java.io.*
import java.nio.charset.StandardCharsets

class Word2Forrest(doc: HWPFDocument, stream: OutputStream) {

    private var out: Writer = OutputStreamWriter(stream, StandardCharsets.UTF_8)

    init {
        init()
        openDocument()
        openBody()
        val r = doc.range
        val styleSheet = doc.styleSheet
        var sectionLevel = 0
        val lenParagraph = r.numParagraphs()
        var inCode = false
        for (x in 0 until lenParagraph) {
            val p = r.getParagraph(x)
            val text = p.text()
            if (text.trim { it <= ' ' }.isEmpty()) {
                continue
            }
            val paragraphStyle = styleSheet.getStyleDescription(p.styleIndex.toInt())
            val styleName = paragraphStyle.name
            if (styleName.startsWith("Heading")) {
                if (inCode) {
                    closeSource()
                    inCode = false
                }
                val headerLevel = styleName.substring(8).toInt()
                if (headerLevel > sectionLevel) {
                    openSection()
                } else {
                    for (y in 0 until sectionLevel - headerLevel + 1) {
                        closeSection()
                    }
                    openSection()
                }
                sectionLevel = headerLevel
                openTitle()
                writePlainText(text)
                closeTitle()
            } else {
                val cruns = p.numCharacterRuns()
                val run = p.getCharacterRun(0)
                val fontName = run.fontName
                if (fontName.startsWith("Courier")) {
                    if (!inCode) {
                        openSource()
                        inCode = true
                    }
                    writePlainText(p.text())
                } else {
                    if (inCode) {
                        inCode = false
                        closeSource()
                    }
                    openParagraph()
                    writePlainText(p.text())
                    closeParagraph()
                }
            }
        }
        for (x in 0 until sectionLevel) {
            closeSection()
        }
        closeBody()
        closeDocument()
        out.flush()
    }

    @Throws(IOException::class)
    fun init() {
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n")
        out.write("<!DOCTYPE document PUBLIC \"-//APACHE//DTD Documentation V1.1//EN\" \"./dtd/document-v11.dtd\">\r\n")
    }

    @Throws(IOException::class)
    fun openDocument() {
        out.write("<document>\r\n")
    }

    @Throws(IOException::class)
    fun closeDocument() {
        out.write("</document>\r\n")
    }

    @Throws(IOException::class)
    fun openBody() {
        out.write("<body>\r\n")
    }

    @Throws(IOException::class)
    fun closeBody() {
        out.write("</body>\r\n")
    }

    @Throws(IOException::class)
    fun openSection() {
        out.write("<section>")
    }

    @Throws(IOException::class)
    fun closeSection() {
        out.write("</section>")
    }

    @Throws(IOException::class)
    fun openTitle() {
        out.write("<title>")
    }

    @Throws(IOException::class)
    fun closeTitle() {
        out.write("</title>")
    }

    @Throws(IOException::class)
    fun writePlainText(text: String?) {
        out.write(text)
    }

    @Throws(IOException::class)
    fun openParagraph() {
        out.write("<p>")
    }

    @Throws(IOException::class)
    fun closeParagraph() {
        out.write("</p>")
    }

    @Throws(IOException::class)
    fun openSource() {
        out.write("<source><![CDATA[")
    }

    @Throws(IOException::class)
    fun closeSource() {
        out.write("]]></source>")
    }
    companion object {
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            FileInputStream(args[0]).use { `is` -> FileOutputStream("test.xml").use { out -> Word2Forrest(HWPFDocument(`is`), out) } }
        }

    }
}