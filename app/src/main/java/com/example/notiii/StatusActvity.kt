package com.example.notiii

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.notiii.R
import com.example.notiii.dataclasses.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

private lateinit var image : ImageView
private lateinit var imagetext : TextView
private var imageuri : Uri?=null
class StatusActvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_actvity)
          image = findViewById (R.id.imageView15)
        imagetext=findViewById(R.id.textView27)
        val description = findViewById<EditText>(R.id.editTextText5)
        val upload = findViewById<Button>(R.id.button7)
        val sharedPreferences : SharedPreferences =getSharedPreferences("mypre2", MODE_PRIVATE)
         val myidea = sharedPreferences.getString("userName","heelo")
        val myidea2 = sharedPreferences.getString("photo","heelo")
        upload.setOnClickListener(){
            if (imageuri!=null&&(description.text.toString()).isNotEmpty()
                        ){
                 val dbstorage = FirebaseStorage.getInstance()
                val dbauth =   FirebaseDatabase.getInstance()
                val id = dbauth.getReference().push().key
                dbstorage.getReference("Stories")
                    .child(id!!)
                    .putFile(imageuri!!)
                    .addOnCompleteListener(){
                        dbstorage.getReference("Stories")
                            .child(id).downloadUrl.addOnSuccessListener {Uri->
                                val myuri = Uri.toString()
                                val status = Status(myidea2!!,myidea!!,description.text.toString(),myuri)
                                dbauth.getReference("Stories").child(id)
                                    .setValue(status).addOnCompleteListener(){
                                        Toast.makeText(this@StatusActvity,"succesfully Shared"
                                        ,Toast.LENGTH_SHORT).show()
                                    }.addOnFailureListener(){

                                    }

                            }
                    }.addOnFailureListener(){

                    }

            }else{
                Toast.makeText(this@StatusActvity,"Cant upload a empty status",
                Toast.LENGTH_SHORT).show()
            }
        }
        image.setOnClickListener(){
            getimage()
        }


    }

    private fun getimage() {
         val intent = Intent()
        intent.action=Intent.ACTION_OPEN_DOCUMENT
        intent.type="image/*"
        startActivityForResult(intent,47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            if(data.data!=null){
                image.setImageURI(data.data)
                imageuri=data.data
                imagetext.visibility= View.GONE
            }
        }else{
            Toast.makeText(this@StatusActvity,
            "Nothing Selected",Toast.LENGTH_SHORT)
                .show()
        }
    }
}