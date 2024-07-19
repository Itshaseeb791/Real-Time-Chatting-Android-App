package com.example.notiii

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class MyChatMainActivity : AppCompatActivity() {
    private lateinit var dbref : FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_chat_main)
        dbref= FirebaseAuth.getInstance()
        sharedPreferences=getSharedPreferences("mypre2", MODE_PRIVATE)
        var myemil =intent.getStringExtra("email")
        var Mypass = intent.getStringExtra("pass")
//        if (myemil=="mu"){
//            if (Mypass=="mu"){
//                myemil=null
//                Mypass=null
//            }
//
//        }
        if (myemil != null) {
            if (Mypass != null) {

                    sharedPreferences.edit().putString("email",myemil).apply()
                    sharedPreferences.edit().putString("password",Mypass).apply()

            }
        }
        dbref.signInWithEmailAndPassword(
            sharedPreferences.getString("email","heelo")!!,
            sharedPreferences.getString("password","hello2")!!
        ).addOnCompleteListener(){
            if (it.isSuccessful){
                val intent= Intent(this@MyChatMainActivity,Homescreen::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this@MyChatMainActivity,chatapp::class.java)
                startActivity(intent)
            }


        }

    }

}