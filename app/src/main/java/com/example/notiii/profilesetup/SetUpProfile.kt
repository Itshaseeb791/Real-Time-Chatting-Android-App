package com.example.notiii.profilesetup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.example.notiii.R

import com.example.notiii.Homescreen
import com.example.notiii.dataclasses.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class SetUpProfile : AppCompatActivity() {
    private lateinit var dbref: FirebaseAuth
    private lateinit var debdataase: FirebaseDatabase
    private lateinit var dbstorage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_up_profile)
        dbstorage = FirebaseStorage.getInstance()
        dbref = FirebaseAuth.getInstance()
        debdataase = FirebaseDatabase.getInstance()
        val username = intent.getStringExtra("username")
        val phone = intent.getStringExtra("phone")
        val image = intent.getStringExtra("image")?.toUri()
        Log.d("log1","$image")
        val email = intent.getStringExtra("email")
        val pass11 = intent.getStringExtra("pass")


        val myreferce = dbstorage.getReference("UserProfile")
                                .child(dbref.uid!!)

        if (image != null) {

            myreferce.putFile(image).addOnCompleteListener() {task->
                if (task.isSuccessful) {

                    myreferce.downloadUrl.addOnSuccessListener { url ->
                        if(url!=null) {
                            val Imageurl = url.toString()
                            val Myobject = UserData(dbref.uid!!,username!!,email!!,pass11!!,phone!!,Imageurl)
                            debdataase.getReference("User")
                                .child(dbref.uid!!)
                                .setValue(Myobject)
                                .addOnCompleteListener() {
                                    if (it.isSuccessful) {

                                        startActivity(
                                            Intent(

                                                this@SetUpProfile,
                                                Homescreen::class.java
                                            )
                                        )
                                    }
                                }.addOnFailureListener() {
                                    Toast.makeText(
                                        this@SetUpProfile,
                                        "Error : ${it.toString()}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }else {
                            Toast.makeText(
                                this@SetUpProfile,
                                "Error : ${ url.toString()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }.addOnFailureListener() {
                        Toast.makeText(
                            this@SetUpProfile,
                            "Error : ${it.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@SetUpProfile, "Error 112", Toast.LENGTH_SHORT
                    ).show()
                }

            }
                .addOnFailureListener() {
                    Toast.makeText(
                        this@SetUpProfile, "Error : ${it.toString()}", Toast.LENGTH_SHORT
                    ).show()
                }
        }

    }
}