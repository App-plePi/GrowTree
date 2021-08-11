package org.techtown.vacationproject337

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val items: ArrayList<Tree>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it -> Toast.makeText(it.context,"${item.title}입니다",Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val inflaterView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_aboutrecycler, parent, false)
        return RecyclerAdapter.ViewHolder(inflaterView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v

        fun bind(listener: View.OnClickListener,item: Tree) {

        }
    }
}