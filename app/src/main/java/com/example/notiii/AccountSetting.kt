package com.example.notiii

import android.app.Instrumentation.ActivityResult
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SignalStrength
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.notiii.databinding.ActivityAccountSettingBinding
import com.example.notiii.dataclasses.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlin.math.log

class AccountSetting : AppCompatActivity() {

    private lateinit var imageIp: String
    private var Myimageuri :Uri?=null
//    private var sameimage : String?=null
    private lateinit var Myid : String
    private lateinit var dbref: FirebaseDatabase
    private lateinit var dbstorage: FirebaseStorage
private lateinit var binding : ActivityAccountSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAccountSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbstorage = FirebaseStorage.getInstance()
        dbref =  FirebaseDatabase.getInstance()
        var mydata = UserData("12","demo","he","12","12","12")
        val auth = FirebaseAuth.getInstance()
        val key = auth.uid
         dbref.getReference("User")
            .child(key!!)
             .addValueEventListener(object : ValueEventListener{
                 override fun onDataChange(snapshot: DataSnapshot) {
                     if (snapshot.exists()){
                         val list = mutableListOf<String>()
                         for (x in snapshot.children){
                             val myobject = x.getValue(String::class.java)
                             if (myobject != null) {
                                 list.add(myobject)
                             }

                         }
                           mydata = UserData(list[5],list[4],list[0],list[2],list[3],list[1])
                        loaddata(mydata)
                     }
                 }

                 override fun onCancelled(error: DatabaseError) {

                 }

             })

//        val Name = intent.getStringExtra("username")
//        val image = intent.getStringExtra("Myimage")
//        sameimage=image!!
//        val phone = intent.getStringExtra("phone")
//        val email = intent.getStringExtra("email")
//        val usid = intent.getStringExtra("uerid")
//        Myid=usid!!
//        val mypasi = intent.getStringExtra("password")
        val username : TextView = findViewById(R.id.mytextView)
        val myemail : TextView = findViewById(R.id.MyemailView)
        val phoneView : TextView = findViewById(R.id.MyphoneView)
//        imageView= findViewById(R.id.imageView12)
        val usernamelayout : LinearLayout = findViewById(R.id.linearLayout11)
        val emaillayout : LinearLayout = findViewById(R.id.linearLayout12)
        val phonelayout : LinearLayout = findViewById(R.id.linearLayout13)
        val savebtn : Button = findViewById(R.id.button6)
//        username.text=Name
//        myemail.text=email
//        phoneView.text=phone
//        Glide.with(this@AccountSetting)
//            .load(image)
//            .error(R.drawable.baseline_account_circle_24)
//            .into(imageView)
        savebtn.setOnClickListener(){
            val dialog = AlertDialog.Builder(this@AccountSetting)
            dialog.setMessage("Trying to update your data in Our database")
            dialog.setTitle("PLease Wait")
                val mydialog = dialog.create()
           val myimageuri =Myimageuri
            val myusername = username.text.toString()
            val myemail1 = myemail.text.toString()
            val mypass = mydata.password
            val phonenumber = phoneView.text.toString()
            val Myuserid =  FirebaseAuth.getInstance().uid
            if  (
                myusername.isNotEmpty()
                &&myemail1.isNotEmpty()
                &&mypass!!.isNotEmpty() && phonenumber.isNotEmpty()
                &&Myuserid!!.isNotEmpty()){
                mydialog.show()
                dbstorage.getReference("UserProfile")
                    .child(Myuserid)
                    .putFile(myimageuri!!)
                    .addOnCompleteListener(){
                        if (it.isSuccessful) {
                            dbstorage.getReference("UserProfile")
                                .child(Myuserid).downloadUrl.addOnSuccessListener { url ->

                                    val Uploaduri = url.toString()
                                    val Newobject = UserData(
                                        Myuserid,
                                        myusername,
                                        myemail1,
                                        mypass,
                                        phonenumber,
                                        Uploaduri
                                    )
                                    dbref.getReference("User").child(Myuserid)
                                        .setValue(Newobject)
                                        .addOnCompleteListener() {
                                            Toast.makeText(
                                                this@AccountSetting,
                                                "Sucessfully Changed",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            mydialog.dismiss()
                                        }.addOnFailureListener() {
                                            Toast.makeText(
                                                this@AccountSetting,
                                                "Image error Error ; ${it.toString()}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            mydialog.dismiss()
                                        }


                                }.addOnFailureListener() {
                                    Toast.makeText(this@AccountSetting
                                        ,"Image error Error ; ${it.toString()}"
                                        ,  Toast.LENGTH_SHORT).show()
                                    mydialog.dismiss()
                                }
                        }
                            else{
                            Toast.makeText(this@AccountSetting
                                ,"Image error Error "
                                ,  Toast.LENGTH_SHORT).show()
                            mydialog.dismiss()
                        }
                    }.
        addOnFailureListener(){
                        Toast.makeText(this@AccountSetting
                        ,"Image error Error ; ${it.toString()}"
                        ,  Toast.LENGTH_SHORT).show()
                        mydialog.dismiss()
                    }
            }else{
                Toast.makeText(this@AccountSetting
                    ,"Kindly Fill all requirments"
                    ,  Toast.LENGTH_SHORT).show()
            }



        }


        usernamelayout.setOnClickListener(){
            alertbar("Username",  username.text.toString(),username)
        }
        emaillayout.setOnClickListener(){
            alertbar("Email",myemail.text.toString(),myemail)
        }
        phonelayout.setOnClickListener(){
            alertbar("Phone",phoneView.text.toString(),phoneView)
        }
         binding.imageView12.setOnClickListener(){

             val intent=Intent(this@AccountSetting,com.example.notiii.ImageView::class.java)
             intent.putExtra("imageViews",mydata.image)
             startActivity(intent)
        }
        binding.imageView12.setOnLongClickListener(){
            showdialog()
            true
        }



    }

    private fun showdialog() {
         val mydialog = AlertDialog.Builder(this@AccountSetting)
        mydialog.setTitle("Updating Iamge")
        mydialog.setMessage("Are you sure you want to update image")
        mydialog.setPositiveButton("Yes",object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                getimage()
            }

        }).setNegativeButton("Cancel",object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {

            }

        })
        mydialog.show()
    }

    private fun getimage() {
         val intent = Intent()
        intent.action=Intent.ACTION_OPEN_DOCUMENT
        intent.type="image/*"
        startActivityForResult(intent, 45)

    }
    private fun loaddata( Myobject : UserData){
      binding.mytextView.text=Myobject.userName
        binding.MyemailView.text=Myobject.email
        binding.MyphoneView.text=Myobject.phone
        Glide.with(this@AccountSetting)
            .load(Myobject.image)
            .error(R.drawable.baseline_account_circle_24)
            .into(binding.imageView12)
        imageIp=Myobject.image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            if (data.data!=null){
                Myimageuri=data.data
                 binding.imageView12.setImageURI(data.data)
//                Myimageuri?.let { putimge(it) }

            }
        }
    }


    private fun alertbar(name : String,username:String,area:TextView){
        val mybuilder = AlertDialog.Builder(this@AccountSetting)
        val myview = LayoutInflater.from(this).inflate(R.layout.alertbarsettings,null)
        mybuilder.setView(myview)
        val newmessage = myview.findViewById<EditText>(R.id.editTextText4)
        mybuilder.setTitle("Updating $name")
        newmessage.setText(username)
        mybuilder.setMessage("Kindly Enter the new $name")
        mybuilder.setPositiveButton("Save",object:DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                 val mynew = newmessage.text.toString()
                if (mynew.isNotEmpty()&&mynew!=username){
                    area.text=mynew

                }
            }

        }).setNegativeButton("canel",object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
//                TODO("Not yet implemented")
            }

        })

        mybuilder.show()
    }
    private fun putimge(myimageuri:Uri) {
        val auth = FirebaseAuth.getInstance()
        val Myuserid = auth.uid

        if (Myuserid != null) {
            dbstorage.getReference("UserProfile")
                .child(Myuserid)
                .putFile(myimageuri)
                .addOnCompleteListener(){
                    if (it.isSuccessful) {
                        dbstorage.getReference("UserProfile")
                            .child(Myuserid).downloadUrl.addOnSuccessListener { url ->
                                if (url != null) {
                                    val sameimage = url.toString()
                                } else {
                                    Toast.makeText(this@AccountSetting
                                        ,"Error in Saving Image"
                                        ,  Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }.addOnFailureListener(){
                    Toast.makeText(this@AccountSetting
                        ,"Error in Saving Image"
                        ,  Toast.LENGTH_SHORT).show()
                }
        }

    }
}