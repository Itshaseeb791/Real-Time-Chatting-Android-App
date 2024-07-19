package com.example.notiii.MyDatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notiii.R
import com.example.notiii.dataclasses.UserData

class SreachUserAdapter (val context : Context, private var MyData : ArrayList<UserData> )
    :RecyclerView.Adapter<SreachUserAdapter.viewHolder>(){


    private lateinit var OnclickListener : ClickListener
    interface ClickListener{
        fun getitem(position: Int)
    }
    fun onClickListerner(MyclickListerner:ClickListener){
      var  OnclickListener=MyclickListerner
    }
    inner class viewHolder(itemView: View, MyclickListerner: ClickListener)
        : RecyclerView.ViewHolder(itemView){
        val image1 : ImageView = itemView.findViewById(R.id.imageView51)
        val username : TextView = itemView.findViewById(R.id.textView181)
        val addoption : ImageView = itemView.findViewById(R.id.imageView13)
        init {
            itemView.setOnClickListener() {
                MyclickListerner.getitem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.sreachadapter,parent,false)
            ,OnclickListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
         holder.username.text=MyData[position].userName
                Glide.with(context)
            .load(MyData[position].image)
            .error(R.drawable.baseline_account_circle_24)
            .into(holder.image1)
        holder.addoption.setImageResource(R.drawable.baseline_person_add_24)
    }

//    override fun onBindViewHolder(holder: UserAdapter.viewHolder, position: Int) {
//        val user3 = MyData[position]
//        Log.d("pic","${user3.image}")
//        holder.username.text=MyData[position].userName
//        Glide.with(context)
//            .load(MyData[position].image)
//            .error(R.drawable.baseline_account_circle_24)
//            .into(holder.image1)
//    }

    override fun getItemCount(): Int {
        return MyData.size
    }

    fun setData(Mydata1 : ArrayList<UserData>){
        this.MyData=Mydata1
        notifyDataSetChanged()
    }
}