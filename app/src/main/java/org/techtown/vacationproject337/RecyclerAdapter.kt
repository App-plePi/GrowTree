package org.techtown.vacationproject337

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val treelist: ArrayList<Tree>) : RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_aboutrecycler,parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return treelist.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.CustomViewHolder, position: Int) {
        holder.tree_img1.setImageResource(treelist[position].tree_Image)
        holder.tree_name1.text = treelist[position].tree_Name
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tree_name1 = itemView.findViewById<TextView>(R.id.tree_name_ly) // 나무이름
        val tree_img1 = itemView.findViewById<ImageView>(R.id.tree_img_ly) // 나무 이미지
    }
}