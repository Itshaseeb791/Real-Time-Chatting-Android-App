package com.example.notiii.adapter

import android.content.Context
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notiii.R
import com.example.notiii.dataclasses.Status

class StatusAdapter( val MyStatus : MutableList<Status>,val context : Context)
    : RecyclerView.Adapter<StatusAdapter.MyviewHolder>() {
    private lateinit var OnclickListener : StatusAdapter.ClickListener
    interface ClickListener{
        fun getitem(position: Int)
    }
    fun onClickListerner(MyclickListerner: StatusAdapter.ClickListener){
        OnclickListener=MyclickListerner
    }
    inner class MyviewHolder(itemView: View,MyclickListerner: StatusAdapter.ClickListener):RecyclerView.ViewHolder(itemView){
        val senderimage : ImageView = itemView.findViewById(R.id.imageView16)
        val sendername : TextView = itemView.findViewById(R.id.textView28)
        val   statusDes : TextView = itemView.findViewById(R.id.textView29)
        val statusImage : ImageView = itemView.findViewById(R.id.imageView17)
        init {
            itemView.setOnClickListener(){
                MyclickListerner.getitem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
         return MyviewHolder(LayoutInflater.from(parent.context).inflate(
             R.layout.statusadapter,parent,false
         ),OnclickListener)
    }

    override fun getItemCount(): Int {
         return MyStatus.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
         holder.sendername.text=MyStatus[position].sender
        holder.statusDes.text=MyStatus[position].des
        Glide.with(context)
            .load(MyStatus[position].senderpic)
            .error(R.drawable.baseline_account_circle_24)
            .into(holder.senderimage)
        Glide.with(context)
            .load(MyStatus[position].imagestatus)
            .error(R.drawable.iccon)
            .into(holder.statusImage)
    }
}