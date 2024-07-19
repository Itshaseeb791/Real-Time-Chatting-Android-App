package com.example.notiii.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notiii.R
import com.example.notiii.dataclasses.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserAdapter(val context : Context,private var MyData : ArrayList<UserData> ) :RecyclerView.Adapter<UserAdapter.viewHolder>(){


    private lateinit var OnclickListener : ClickListener
    private val dbdatbase = FirebaseDatabase.getInstance()
    private val dbaut = FirebaseAuth.getInstance()
    interface ClickListener{
        fun getitem(position: Int)
    }
    fun onClickListerner(MyclickListerner: ClickListener){
        OnclickListener=MyclickListerner
    }
    inner class viewHolder(itemView: View, MyclickListerner: ClickListener)
        :RecyclerView.ViewHolder(itemView){
        val image1 : ImageView = itemView.findViewById(R.id.imageView5)
        val username : TextView = itemView.findViewById(R.id.textView12)
        val notifi : CardView = itemView.findViewById(R.id.notification)
        init {
            itemView.setOnClickListener() {
                MyclickListerner.getitem(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chatsadapter,parent,false)
        ,OnclickListener)
    }

    override fun getItemCount(): Int {
         return MyData.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val user3 = MyData[position]
        Log.d("pic","${user3.image}")
         holder.username.text=MyData[position].userName
         Glide.with(context)
             .load(MyData[position].image)
             .error(R.drawable.baseline_account_circle_24)
             .into(holder.image1)
        if (user3.password=="Notifily"){
            holder.notifi.visibility=View.VISIBLE
        }



    }



    fun setData(Mydata1 : ArrayList<UserData>){
        this.MyData=Mydata1
        notifyDataSetChanged()
    }
}