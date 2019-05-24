package cc.wenmin92.androidlearn.view.viewpager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cc.wenmin92.androidlearn.R

class ListFragment : Fragment() {

    private var mParam: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam = arguments!!.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvList = view.findViewById<RecyclerView>(R.id.rv_list)
        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.adapter = MyAdapter(mParam)
    }

    internal class MyAdapter(private val mParam: Int) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false))
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.tvItem.text = String.format("%s - %s", mParam, position)
        }

        override fun getItemCount(): Int {
            return 100
        }
    }

    internal class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvItem: TextView

        init {
            tvItem = itemView.findViewById(R.id.tv_item)
        }
    }

    companion object {
        private val ARG_PARAM1 = "param1"

        internal fun newInstance(number: Int): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, number)
            fragment.arguments = args
            return fragment
        }
    }
}
