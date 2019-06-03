package cc.wenmin92.androidlearn.widget.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import cc.wenmin92.androidlearn.R

/**
 * 现实嵌套的方法有很多，参考语雀文档
 */
class NestRecyclerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nest_recycler)

        val rv1 = findViewById<RecyclerView>(R.id.list1)
        val rv2 = findViewById<RecyclerView>(R.id.list2)

        for (rv in listOf<RecyclerView>(rv1, rv2)) {
            rv.adapter = ListAdapter(List(20) { "item $it" })
            rv.setHasFixedSize(true)
            rv.isNestedScrollingEnabled = false
        }
    }
}

class ListAdapter(private val data: List<String>) : Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvItem.text = data[position]
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvItem: TextView = itemView.findViewById(R.id.tv_item)
}
