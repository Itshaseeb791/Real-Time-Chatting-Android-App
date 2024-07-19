package com.example.notiii.MyDatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notiii.R


class ListAdpater() : RecyclerView.Adapter<ListAdpater.view_holder>() {
    private lateinit var data: List<User>
    private lateinit var OnclickListener : ClickListener
    interface ClickListener{
        fun getitem(position: Int)
    }
     fun onClickListerner(MyclickListerner:ClickListener){
        OnclickListener=MyclickListerner
    }
    inner class view_holder(itemView: View,MyclickListerner:ClickListener)
        : RecyclerView.ViewHolder(itemView){
        val area : TextView = itemView.findViewById(R.id.textView2)
        init {
               itemView.setOnClickListener() {
                   MyclickListerner.getitem(adapterPosition)
               }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): view_holder {
        return view_holder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter,parent,
                false),OnclickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: view_holder, position: Int) {
        holder.area.text=data[position].note
    }
    fun setdata(data1 : List<User>){
        this.data=data1
        notifyDataSetChanged()
    }
}