package com.example.notiii

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notiii.R
import com.example.notiii.adapter.StatusAdapter
import com.example.notiii.dataclasses.Status
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GroupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view= inflater.inflate(R.layout.fragment_group, container, false)
        val addbtn = view.findViewById<TextView>(R.id.textView23)
        val recyleVi = view.findViewById<RecyclerView>(R.id.myrecycleview3)
        recyleVi.layoutManager=LinearLayoutManager( requireContext())

        val database = FirebaseDatabase.getInstance()
        database.getReference("Stories")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                     if (snapshot.exists()){
                         val list : MutableList<Status> = mutableListOf(Status("0","0","0","0"))
                         list.clear()
                         for (i in snapshot.children){
                             val status = i.getValue(Status::class.java)
                             list.add(status!!)
                         }

                             val adapter1 =StatusAdapter(list,requireContext())
                                recyleVi.scrollToPosition(list.size-1)
                         recyleVi.adapter=adapter1
                         adapter1.onClickListerner(object : StatusAdapter.ClickListener{
                             override fun getitem(position: Int) {
                                  val intent = Intent(requireContext(),ImageView::class.java)
                                 intent.putExtra("imageViews",list[position].imagestatus)
                                 startActivity(intent)
                             }

                         })
                     }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        addbtn.setOnClickListener(){
            startActivity(Intent(requireContext(),StatusActvity::class.java))
        }

        return view
    }


}