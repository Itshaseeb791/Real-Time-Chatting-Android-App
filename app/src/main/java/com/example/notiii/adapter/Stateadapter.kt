package com.example.notiii.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notiii.ChatsFragment
import com.example.notiii.GroupFragment

class stateadapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment {
      return when(position){
          0-> ChatsFragment()
          1-> GroupFragment()
          else->throw IllegalArgumentException("Error : Invalid Tab")
      }
    }
}