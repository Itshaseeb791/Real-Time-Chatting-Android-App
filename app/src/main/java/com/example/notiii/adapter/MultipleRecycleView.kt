package com.example.notiii.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notiii.R
import com.example.notiii.dataclasses.Message
import com.google.firebase.auth.FirebaseAuth

class MultipleRecycleView(val Data : ArrayList<Message>, val context: Context,
                          val id : String):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private   var dbref : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var OnclickListener : ClickListener
    private lateinit var OnLongclickListener : LongClickListener
    interface ClickListener{
        fun getitem(position: Int)
    }
    interface LongClickListener {
          fun getitem(position: Int)
    }
    fun onClickListerner(MyclickListerner: ClickListener){
        OnclickListener=MyclickListerner
    }
    fun onLongClickListerner(MyclickListerner: LongClickListener){
        OnLongclickListener=MyclickListerner
    }

    inner class senderClass(itemView: View,MyclickListerner: MultipleRecycleView.ClickListener):RecyclerView.ViewHolder(itemView) {
        val smessage: TextView = itemView.findViewById(R.id.textView13)

        init {
            itemView.setOnLongClickListener() {
                MyclickListerner.getitem(adapterPosition)
                true
            }

        }
    }
    inner class reciverClass(itemView: View,MyclickListerner: MultipleRecycleView.ClickListener):RecyclerView.ViewHolder(itemView){
        val rmessage : TextView = itemView.findViewById(R.id.textView14)
        init {
            itemView.setOnLongClickListener() {
                MyclickListerner.getitem(adapterPosition)
                true
            }

        }
    }
    inner class Sendedimage(
        itemView: View, MyclickListerner: ClickListener
        , Mylonglistener: LongClickListener
    ):RecyclerView.ViewHolder(itemView){
         val image : ImageView = itemView.findViewById(R.id.imageView181)
        init {
            itemView.setOnLongClickListener() {
                MyclickListerner.getitem(adapterPosition)
                true
            }
            itemView.setOnClickListener(){
                Mylonglistener.getitem(adapterPosition)

            }

        }
    }
    inner class Reciveclass(itemView: View,MyclickListerner: MultipleRecycleView.ClickListener
    ,Mylonglistener: LongClickListener
    ):RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.imageView183)
        init {
            itemView.setOnLongClickListener() {
                MyclickListerner.getitem(adapterPosition)
                true
            }
            itemView.setOnClickListener(){
                Mylonglistener.getitem(adapterPosition)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return when(viewType){
          0->senderClass(LayoutInflater.from(parent.context).inflate(R.layout.sendermessage,parent,false)
          , OnclickListener)
          2->Sendedimage(LayoutInflater.from(parent.context).inflate(R.layout.sendingimage,parent,false)
          , OnclickListener,OnLongclickListener)
          3->Reciveclass(LayoutInflater.from(parent.context).inflate(R.layout.recivepicture,parent,false)
              , OnclickListener,OnLongclickListener)
          else->reciverClass(LayoutInflater.from(parent.context).inflate(R.layout.recivermessage,parent,false)
          ,OnclickListener)


      }
    }

    override fun getItemCount(): Int {
     return Data.size
    }

    override fun getItemViewType(position: Int): Int {

        return when(Data[position].type){
            "sender"->0
            "Picture"->2
            "Picture2"->3
            else->1

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)==0){
             val senderClass1 = holder as senderClass
            senderClass1.smessage.text=Data[position].message

        }else if(getItemViewType(position)==2){
            val sendedimage1 = holder as Sendedimage
            Glide.with(context)
                .load(Data[position].message)
                .error(R.drawable.baseline_account_circle_24)
                .into(sendedimage1.image)
        }else if(getItemViewType(position)==3){
            val sendedimage1 = holder as Reciveclass
            Glide.with(context)
                .load(Data[position].message)
                .error(R.drawable.baseline_account_circle_24)
                .into(sendedimage1.image)
        }
        else{
            val reciverClass1 = holder as reciverClass
            reciverClass1.rmessage.text=Data[position].message
        }

    }
}