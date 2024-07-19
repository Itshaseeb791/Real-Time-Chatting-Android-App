package com.example.notiii.profilesetup

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.notiii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class signUp : AppCompatActivity() {
    private lateinit var dbref: FirebaseAuth
    private lateinit var debdataase: FirebaseDatabase
    private lateinit var dbstorage: FirebaseStorage
    var selectedImage: Uri? = null
    private lateinit var image: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbstorage = FirebaseStorage.getInstance()
        dbref = FirebaseAuth.getInstance()
        debdataase = FirebaseDatabase.getInstance()
        setContentView(R.layout.activity_sign_up)
        image = findViewById(R.id.imageView3)
        image.setImageResource(R.drawable.baseline_account_circle_24)
        val email: EditText = findViewById(R.id.textView7)
        val pass1: EditText = findViewById(R.id.textView8)
        val pass2: EditText = findViewById(R.id.textView9)
        val user: EditText = findViewById(R.id.textView6)
        val phone: EditText = findViewById(R.id.textView10)
        val btn: Button = findViewById(R.id.button5)
        image.setOnClickListener() {
            if (gallerypermissiom()){
                getimage()
            }else{
                requestForPermissions()
            }

        }

        btn.setOnClickListener() {
//            debdataase.getReference("demi")
//                .child("user").setValue(UserData("01","01","01","01,","01","01"))
//
////            if (dbref.uid != null) {
//
//
            val email1 = email.text.toString()
            val pass11 = pass1.text.toString()
            val pass21 = pass2.text.toString()
            val username = user.text.toString()
            val phoneNumber = phone.text.toString()
            if (email1.isNotEmpty() && pass11.isNotEmpty() && pass21.isNotEmpty()
                && username.isNotEmpty() && phoneNumber.isNotEmpty()
            ) {
                if(pass11.length<6){
                    Toast.makeText(this@signUp,"Password Should be Greater then 6 characters"
                    ,Toast.LENGTH_SHORT).show()
                }else if(phoneNumber.length<11){
                    Toast.makeText(this@signUp,"Invalid Phone Number"
                        ,Toast.LENGTH_SHORT).show()
                }else if (selectedImage == null) {
                        Toast.makeText(this@signUp,"please Click at image view and select image"
                            ,Toast.LENGTH_SHORT).show()
                    }

                  if(pass11 == pass21) {
                    dbref.createUserWithEmailAndPassword(
                        email1,
                        pass21
                    ).addOnCompleteListener() {
                        Toast.makeText(
                            this@signUp,
                            "Profile is created Succesfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@signUp, SetUpProfile::class.java)
                        intent.putExtra("username", username)
                        intent.putExtra("phone", phoneNumber)
                        intent.putExtra("image", selectedImage.toString())

                        intent.putExtra("email",email1)
                        intent.putExtra("pass",pass21)
                        startActivity(intent)

                    }.addOnFailureListener() {
                        Toast.makeText(
                            this@signUp,
                            "Error : ${it.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }else{
                      Toast.makeText(this@signUp,"Password and confirmed password should be equal"
                          ,Toast.LENGTH_SHORT).show()
                  }
            }else{
                Toast.makeText(this@signUp,"Please Fill all the details"
                    ,Toast.LENGTH_SHORT).show()
            }
        }
    }
private fun getimage(){
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.type = "image/*"
    startActivityForResult(intent, 45)
}
    private fun requestForPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),101)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==101){
            if (grantResults.size>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getimage()
            }else{
                alertbar("Gallery Permission","Cant Pic an image from gallery with this permission")
            }

        }else{
            Toast.makeText(this@signUp,"Invalid Permission",Toast.LENGTH_SHORT).show()
        }
    }
    private fun alertbar(hardere : String,message : String){
        val alert = AlertDialog.Builder(this)
//                val alert1 = alert.create()
        alert.setTitle("$hardere Permission")
        alert.setMessage(message)
        alert.setPositiveButton("Allow", DialogInterface.OnClickListener { dialogInterface, i ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package",packageName,null)
            intent.setData(uri)
            startActivity(intent)

        })
        alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
//                    alert1.dismiss()
        })
        alert.show()
    }

    private fun gallerypermissiom(): Boolean {
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                    super.onActivityResult(requestCode, resultCode, data)
                    if (data != null) {
                        if (data.data != null) {
                            val url = data.data

                            selectedImage = data.data
                            image.setImageURI(url)
                        }
                    }
                }
            }


