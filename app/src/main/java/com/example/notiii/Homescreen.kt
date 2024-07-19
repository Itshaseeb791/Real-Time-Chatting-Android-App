package com.example.notiii

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.example.notiii.R
import com.example.notiii.adapter.stateadapter
import com.example.notiii.notepad.MainActivity
import com.example.notiii.service.NotificationService
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.FirebaseDatabase

class Homescreen : AppCompatActivity() {
    private lateinit var viewpage: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var    dbdatabase : FirebaseDatabase
    private lateinit var username : String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        tabLayout=findViewById(R.id.tabLayout)
        viewpage=findViewById(R.id.myview)
        dbdatabase=FirebaseDatabase.getInstance()
        val sharedPreferences = getSharedPreferences("mypre2",
            AppCompatActivity.MODE_PRIVATE
        )
        val serviceValue = sharedPreferences.getString("Myvalue","true")
//        Toast.makeText(this@Homescreen,"$serviceValue",Toast.LENGTH_SHORT).show()
        if (serviceValue=="true"){
            startService(Intent(this@Homescreen, NotificationService::class.java))
        }

          username = sharedPreferences.getString("userName","User1").toString()

        dbdatabase.getReference("UserStatus")
            .child(username!!)
            .setValue(userIStatus(username!!,"online"))

        tabLayout.addTab(tabLayout.newTab().setText("Chats"))
        tabLayout.addTab(tabLayout.newTab().setText("Status"))

        viewpage.adapter= stateadapter(this)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewpage.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        viewpage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        dbdatabase.getReference("UserStatus")
            .child(username!!)
            .setValue(userIStatus(username!!,"Ofline"))
        startActivity(Intent(this@Homescreen, MainActivity::class.java))
    }
}