package com.example.notiii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.notiii.R
import com.example.notiii.notepad.MainActivity
import com.example.notiii.profilesetup.signUp
import com.google.firebase.auth.FirebaseAuth

class chatapp : AppCompatActivity() {
    private lateinit var dbref : FirebaseAuth
//    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatapp)
        dbref= FirebaseAuth.getInstance()
//        sharedPreferences=getSharedPreferences("My_ref", MODE_PRIVATE)
        val email : EditText = findViewById(R.id.editTextTextEmailAddress)
        val password : EditText =  findViewById(R.id.editTextTextPassword)
        val layout1 : LinearLayout = findViewById(R.id.layouy1)

        val sinup : TextView = findViewById(R.id.button3)
        val login : Button = findViewById(R.id.button4)
//        check()




        sinup.setOnClickListener (){
            startActivity(Intent(this@chatapp, signUp::class.java))
        }
        login.setOnClickListener(){
            val email1=email.text.toString()
            val pass1 = password.text.toString()
            if (email1.isNotEmpty()&&pass1.isNotEmpty()){
                dbref.signInWithEmailAndPassword(email1,pass1)
                    .addOnCompleteListener(){
                        if (it.isSuccessful){
//                            sharedPreferences.edit().putString("email",email1).apply()
//                            sharedPreferences.edit().putString("password",pass1).apply()
                            val intent= Intent(this@chatapp,MyChatMainActivity::class.java)
                            intent.putExtra("email",email1)
                            intent.putExtra("pass",pass1)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this@chatapp,"ID Not Found"
                                , Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener(){
                        Toast.makeText(this@chatapp,"ERROR : ${it.message}"
                            , Toast.LENGTH_SHORT).show()
                    }
            }else{
                Toast.makeText(this@chatapp,"please Fill Email and password"
                    , Toast.LENGTH_SHORT).show()
            }
        }

    }
//
//    private fun check()  {
//        dbref.signInWithEmailAndPassword(
//            sharedPreferences.getString("email","heelo")!!,
//            sharedPreferences.getString("password","hello2")!!
//        ).addOnCompleteListener(){
//            if (it.isSuccessful){
//
//                val intent= Intent(this@chatapp,Homescreen::class.java)
//                startActivity(intent)
//
//            }
//            login.visibility=View.VISIBLE
//            sinup.visibility=View.VISIBLE
//            layout1.visibility=View.VISIBLE
//
//        }
//    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@chatapp,MainActivity::class.java))
    }
}