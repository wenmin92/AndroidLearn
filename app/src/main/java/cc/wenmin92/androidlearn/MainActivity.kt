package cc.wenmin92.androidlearn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cc.wenmin92.androidlearn.graphics.canvas.CanvasTestActivity
import cc.wenmin92.androidlearn.graphics.paint.other.ShadowLayerActivity
import cc.wenmin92.androidlearn.graphics.paint.path_effect.PathEffectTestActivity
import cc.wenmin92.androidlearn.graphics.paint.shader.ShaderTestActivity
import cc.wenmin92.androidlearn.jetpack.lifecycle.LifecycleActivity
import cc.wenmin92.androidlearn.keyboard.KeyboardActivity
import cc.wenmin92.androidlearn.net.NetTestActivity
import cc.wenmin92.androidlearn.net.wifi.WiFiTestActivity
import cc.wenmin92.androidlearn.service.location.LocationActivity
import cc.wenmin92.androidlearn.storage.StorageTestActivity
import cc.wenmin92.androidlearn.text.TextTestActivity
import cc.wenmin92.androidlearn.view.anim.AddViewAnimActivity
import cc.wenmin92.androidlearn.view.anim.AnimActivity
import cc.wenmin92.androidlearn.view.constraint.ConstraintActivity
import cc.wenmin92.androidlearn.view.custom.CustomViewActivity
import cc.wenmin92.androidlearn.view.popupwindow.PopupWindowActivity
import cc.wenmin92.androidlearn.view.statusbar.StatusBarTestActivity
import cc.wenmin92.androidlearn.view.style.StyleActivity
import cc.wenmin92.androidlearn.view.viewpager.ViewPagerRvActivity
import cc.wenmin92.androidlearn.widget.recyclerview.NestRecyclerActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val testItems: ArrayList<TestItem>
        get() {
            val testItems = ArrayList<TestItem>()
            testItems.add(TestItem(LocationActivity::class.java, "位置服务"))
            testItems.add(TestItem(ConstraintActivity::class.java, "ConstraintLayout"))
            testItems.add(TestItem(KeyboardActivity::class.java, "监听键盘弹起"))
            testItems.add(TestItem(StyleActivity::class.java, "Style 样式"))
            testItems.add(TestItem(AnimActivity::class.java, "anim"))
            testItems.add(TestItem(AddViewAnimActivity::class.java, "add view anim"))
            testItems.add(TestItem(CustomViewActivity::class.java, "custom view"))
            testItems.add(TestItem(PathEffectTestActivity::class.java, "Paint-Path Effect"))
            testItems.add(TestItem(ShaderTestActivity::class.java, "Paint-Shader"))
            testItems.add(TestItem(StatusBarTestActivity::class.java, "Status Bar"))
            testItems.add(TestItem(StorageTestActivity::class.java, "Storage"))
            testItems.add(TestItem(PopupWindowActivity::class.java, "Popup Window"))
            testItems.add(TestItem(ShadowLayerActivity::class.java, "Paint-ShadowLayer"))
            testItems.add(TestItem(CanvasTestActivity::class.java, "Canvas"))
            testItems.add(TestItem(ViewPagerRvActivity::class.java, "ViewPagerRv"))
            testItems.add(TestItem(TextTestActivity::class.java, "Text"))
            testItems.add(TestItem(NestRecyclerActivity::class.java, "NestRecyclerView"))
            testItems.add(TestItem(WiFiTestActivity::class.java, "WifiTest"))
            testItems.add(TestItem(NetTestActivity::class.java, "NetTest"))
            testItems.add(TestItem(LifecycleActivity::class.java, "Lifecycle"))

            return testItems
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = MyAdapter(testItems) { startActivity(it) }
        rv.adapter = adapter
    }

    private fun startActivity(item: TestItem) {
        startActivity(Intent(this@MainActivity, item.activityClass))
    }

    internal class MyAdapter(private val testItems: List<TestItem>,
                             private val listener: (TestItem) -> Unit)
        : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test_item, parent, false)
            return MyViewHolder(view, listener)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val testItem = testItems[position]
            holder.bind(testItem)
            holder.tvName.text = testItem.name
        }

        override fun getItemCount(): Int {
            return testItems.size
        }
    }

    internal class MyViewHolder(itemView: View, listener: (TestItem) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private var testItem: TestItem? = null
        var tvName: TextView = itemView.findViewById(R.id.tv_name)

        init {
            itemView.setOnClickListener { listener(testItem!!) }
        }

        fun bind(testItem: TestItem) {
            this.testItem = testItem
        }
    }

    internal class TestItem(var activityClass: Class<out Activity>, var name: String)
}
