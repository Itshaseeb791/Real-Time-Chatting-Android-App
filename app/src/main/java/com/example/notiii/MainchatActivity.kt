package com.example.notiii

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notiii.R
import com.example.notiii.adapter.MultipleRecycleView
import com.example.notiii.dataclasses.Message
import com.example.notiii.dataclasses.Notification
import com.example.notiii.dataclasses.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class MainchatActivity : AppCompatActivity() {
    private lateinit var dbref : FirebaseAuth
    private lateinit var dbdatbase : FirebaseDatabase
    private var imageuri : Uri?=null
    private lateinit var senderRoom : String
    private lateinit var ReciverRoom:String
    private lateinit var usid: String
    private lateinit var Myname: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainchat)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        dbref=FirebaseAuth.getInstance()
        dbdatbase= FirebaseDatabase.getInstance()
        val image : ImageView = findViewById(R.id.imageView4)
        val name : TextView = findViewById(R.id.textView14)
        val nam1 = intent.getStringExtra("UserName")
        val im = intent.getStringExtra("image")

          usid = intent.getStringExtra("iduid").toString()
          Myname = intent.getStringExtra("name").toString()
 //       startService(Intent(this@MainchatActivity,NotificationService::class.java))
        Glide.with(this@MainchatActivity)
            .load(im)
            .into(image)
        name.text=nam1
          senderRoom  = dbref.uid!!+usid
         ReciverRoom  = usid+dbref.uid!!
        var message1 :  ArrayList<Message> = arrayListOf(Message("1","1","1","1","0"))
        val message :TextView = findViewById(R.id.editTextText3)
        val send : ImageView = findViewById(R.id.imageView8)
        val recyclerView : RecyclerView=findViewById(R.id.myview1)
        val addbtn : ImageView = findViewById(R.id.imageView61)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(this@MainchatActivity)
        val sharedPreferences : SharedPreferences =getSharedPreferences("mypre2",
            AppCompatActivity.MODE_PRIVATE
        )
        val wallpaper = sharedPreferences.getString("Mywallpaper","null")
        if (wallpaper!="null"){
            val backgroundDrawable = wallpaper?.let { ImageUtils.getDrawableFromUri(this, it.toUri()) }
            recyclerView.background= backgroundDrawable
        }
        dbdatbase.getReference("Friendss").child(dbref.uid!!)
            .child("Friend")
            .child(usid)
            .child("password")
            .setValue("delivered")

        dbdatbase.getReference("Chats")
            .child(senderRoom)
            .child("message")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                     if(snapshot.exists()){
                         message1.clear()
                         for (x in snapshot.children){
                              val myobject = x.getValue(Message::class.java)
                             if (myobject != null) {
                                 message1.add(myobject)
                             }
                         }
                         recyclerView.scrollToPosition(message1.size-1)
                         val newAdapter = MultipleRecycleView(message1,this@MainchatActivity
                             , dbref.uid!!
                         )
                         recyclerView.adapter= newAdapter
                         newAdapter.onClickListerner(object:MultipleRecycleView.ClickListener{
                             override fun getitem(position: Int) {
                                val Mydialog = AlertDialog.Builder(this@MainchatActivity)
                                 Mydialog.setTitle("Delete Message")
                                 Mydialog.setMessage("Are You sure you want to delete this message")
                                 Mydialog.setPositiveButton("Delete From everyone",object :DialogInterface.OnClickListener{
                                     override fun onClick(p0: DialogInterface?, p1: Int) {
                                         dbdatbase.getReference("Chats")
                                             .child(senderRoom)
                                             .child("message")
                                             .child(message1[position].id)
                                             .removeValue().addOnCompleteListener(){
                                                 dbdatbase.getReference("Chats"
                                                 ).child(ReciverRoom)
                                                     .child("message")
                                                     .child(message1[position].id)
                                                     .removeValue().addOnCompleteListener(){
                                                         Toast.makeText(this@MainchatActivity,"Deleted Succesfully From everyone"
                                                             ,Toast.LENGTH_SHORT).show()
                                                     }.addOnFailureListener(){
                                                         Toast.makeText(this@MainchatActivity,"Having error Error : ${it.toString()}"
                                                             ,Toast.LENGTH_SHORT).show()
                                                     }
                                             }.addOnFailureListener(){
                                                 Toast.makeText(this@MainchatActivity,"Having error Error : ${it.toString()}"
                                                     ,Toast.LENGTH_SHORT).show()
                                             }
                                     }

                                 }).setNegativeButton("Delete From Me",object : DialogInterface.OnClickListener{
                                     override fun onClick(p0: DialogInterface?, p1: Int) {
                                         dbdatbase.getReference("Chats")
                                             .child(senderRoom)
                                             .child("message")
                                             .child(message1[position].id)
                                             .removeValue().addOnCompleteListener(){
                                                 Toast.makeText(this@MainchatActivity,"Deleted Succesfully From Me"
                                                     ,Toast.LENGTH_SHORT).show()
                                             }.addOnFailureListener(){
                                                 Toast.makeText(this@MainchatActivity,"Having error Error : ${it.toString()}"
                                                     ,Toast.LENGTH_SHORT).show()
                                             }
                                     }

                                 }).show()
                             }

                         }

                         )
                         newAdapter.onLongClickListerner(object : MultipleRecycleView.LongClickListener{
                             override fun getitem(position: Int) {
                                  val intent = Intent(this@MainchatActivity, com.example.notiii.ImageView::class.java)
                                 val imagrdata = message1[position].message
                                 intent.putExtra("imageViews",imagrdata )
                                 startActivity(intent)

                             }

                         })


                     }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        val sendimage:ImageView= findViewById(R.id.imageView7)
        sendimage.setOnClickListener(){
             val intent = Intent()
            intent.action=Intent.ACTION_OPEN_DOCUMENT
            intent.type="image/*"
            startActivityForResult(intent,24)


        }

        send.setOnClickListener(){

            val mymessagee : String = message.text.toString()
            if (mymessagee.isNotEmpty()){
                message.text=""
                  val message_id = dbdatbase.getReference().push().key
                 val myobject = message_id?.let { it1 -> Message(it1,mymessagee,senderRoom,dbref.uid!!,"sender") }
                if (message_id != null) {
                    dbdatbase.getReference("Chats")
                        .child(senderRoom)
                        .child("message")
                        .child(message_id)
                        .setValue(myobject).addOnSuccessListener {
                            val myobject3 = message_id?.let { it1 -> Message(it1,mymessagee,senderRoom,dbref.uid!!,"reciver") }
                            dbdatbase.getReference("Chats"
                            ).child(ReciverRoom)
                                .child("message")
                                .child(message_id)
                                .setValue(myobject3)
                                .addOnSuccessListener {
                                     val notificatiod_id = dbdatbase.getReference().push().key
                                    dbdatbase.getReference("Notification")
                                        .child(usid!!)
                                        .child("MessageNotifivcation")
                                        .child(notificatiod_id!!)
                                        .setValue(Notification(notificatiod_id,mymessagee,Myname!!,"Unnotified"))
                                        .addOnCompleteListener(){
                                                 dbdatbase.getReference("Friendss").child(usid)
                                                     .child("Friend")
                                                     .child(dbref.uid!!)
                                                     .child("password")
                                                     .setValue("Notifily")
                                                     .addOnCompleteListener(){
                                                         Toast.makeText(this@MainchatActivity,"it.toString()",Toast.LENGTH_SHORT).show()
                                                     }.addOnFailureListener(){
                                                         Toast.makeText(this@MainchatActivity,it.toString(),Toast.LENGTH_SHORT).show()
                                                     }
                                        }.addOnFailureListener(){
                                            Toast.makeText(this@MainchatActivity,it.toString(),Toast.LENGTH_SHORT).show()
                                        }

                                }.addOnFailureListener(){
                                    Toast.makeText(this@MainchatActivity,it.toString(),Toast.LENGTH_SHORT).show()
                                }
                        }.addOnFailureListener(){
                            Toast.makeText(this@MainchatActivity,it.toString(),Toast.LENGTH_SHORT).show()
                        }
                }

            }else{
                Toast.makeText(this@MainchatActivity,"Cant Send Empty Message"
                ,Toast.LENGTH_SHORT).show()
            }
        }
        addbtn.setOnClickListener(){
            dbdatbase.getReference("Friendss")
                .child(dbref.uid!!)
                .child("Friend")
                .child(usid.toString())
                .setValue(UserData(usid!!,nam1!!,"muh201@gmail.com","00","00",im!!))
                .addOnCompleteListener(){
                    if (it.isSuccessful){
                        Toast.makeText(this@MainchatActivity
                        ,"Succesfully Added To Your Friendslist",Toast.LENGTH_SHORT)
                            .show()
                    }
                }.addOnFailureListener(){
                    Toast.makeText(this@MainchatActivity
                        ,"Having error Error : ${it.toString()}",Toast.LENGTH_SHORT)
                        .show()
                }

        }
//        dbdatbase.getReference("Notification")
//            .child(dbref.uid!!)
//            .child("MessageNotifivcation")
//            .addChildEventListener(object : ChildEventListener{
//                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    if (snapshot.exists()){
//                        val list = arrayListOf<String>()
//                      for(x in snapshot.children){
//                          val mystring = x.getValue(String::class.java)
//                          list.add(mystring!!)
////                          val myobject = x.getValue(Notification::class.java)
////                          Log.d("log","here $myobject")
////                          shownotification(myobject)
//                      }
//                        shownotification(Notification(list[0],list[1],list[3],list[2]))
//
//                    }
//                }
//
//                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//
//                }
//
//                override fun onChildRemoved(snapshot: DataSnapshot) {
//
//                }
//
//                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                     Toast.makeText(this@MainchatActivity,error.toString()
//                     ,Toast.LENGTH_SHORT).show()
//                }
//
//            })

    }
    private fun sendimage(senderRoom:String,ReciverRoom:String,usid:String,Myname:String){
        if (imageuri!=null){
            val mydialog = AlertDialog.Builder(this@MainchatActivity)
            mydialog.setTitle("Sending image")
            val view = layoutInflater.inflate(R.layout.imageview,null)

            mydialog.setView(view)
            val image : ImageView = view.findViewById(R.id.imageView1866)
            val dbstorage = FirebaseStorage.getInstance()
            image.setImageURI(imageuri!!)
            mydialog.setPositiveButton("Send",object :DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    val id = dbdatbase.getReference().push().key
                    dbstorage.getReference("Chats")
                        .child(id!!)
                        .putFile(imageuri!!)
                        .addOnCompleteListener(){
                            dbstorage.getReference("Chats").child(id!!).downloadUrl
                                .addOnSuccessListener {Uri->
                                    val Myurl = Uri
                                    val message = Message(id,Myurl.toString(), senderRoom,dbref.uid!!,"Picture")
                                    dbdatbase.getReference("Chats")
                                        .child(senderRoom)
                                        .child("message")
                                        .child(id)
                                        .setValue(message)
                                        .addOnCompleteListener(){
                                            val message2 = Message(id,Myurl.toString(), senderRoom,dbref.uid!!,"Picture2")
                                            dbdatbase.getReference("Chats"
                                            ).child(ReciverRoom)
                                                .child("message")
                                                .child(id)
                                                .setValue(message2)
                                                .addOnCompleteListener(){
                                                    dbdatbase.getReference("Notification")
                                                        .child(usid!!)
                                                        .child("MessageNotifivcation")
                                                        .child(id)
                                                        .setValue(Notification(id,Myurl.toString(),Myname!!,"Unnotified"))
                                                        .addOnCompleteListener(){
                                                            Toast.makeText(this@MainchatActivity,"Suessfully Sended",Toast.LENGTH_SHORT
                                                            ).show()
                                                        }.addOnFailureListener(){
                                                            Toast.makeText(this@MainchatActivity,"having error Error : ${it.toString()}"
                                                                ,Toast.LENGTH_SHORT).show()
                                                        }
                                                }.addOnFailureListener(){
                                                    Toast.makeText(this@MainchatActivity,"having error Error : ${it.toString()}"
                                                        ,Toast.LENGTH_SHORT).show()
                                                }
                                        }.addOnFailureListener(){
                                            Toast.makeText(this@MainchatActivity,"having error Error : ${it.toString()}"
                                                ,Toast.LENGTH_SHORT).show()
                                        }

                                }.addOnFailureListener(){
                                    Toast.makeText(this@MainchatActivity,"having error Error : ${it.toString()}"
                                        ,Toast.LENGTH_SHORT).show()
                                }
                        }.addOnFailureListener(){
                            Toast.makeText(this@MainchatActivity,"having error Error : ${it.toString()}"
                                ,Toast.LENGTH_SHORT).show()
                        }
                }

            }).setNegativeButton("Cancel",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }

            })
            val mynewdialog = mydialog.create()
            mynewdialog.show()
//
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            if (data.data!=null){
                imageuri=data.data
                sendimage(senderRoom,ReciverRoom,usid!!,Myname!!)

            }
        }else{
            Toast.makeText(this@MainchatActivity,"Nothing Selected",Toast.LENGTH_SHORT)
                .show()
        }
    }

//    private fun shownotification(myobject: Notification?) {
//        val id = 5
//        val CHENAL_ID = "101"
//        createNotifactionChenal()
//        val notification = NotificationCompat.Builder(this,CHENAL_ID)
//            .setContentTitle(myobject?.senderName)
//            .setSmallIcon(R.drawable.iccon)
//            .setContentText(myobject?.message)
////            .setSmallIcon(R.drawable.baseline_camera_alt_24)
//            .setPriority(NotificationManager.IMPORTANCE_MAX)
////            .setContentIntent(pendingIntent)
//            .build()
//        val notificationManager = NotificationManagerCompat.from(this)
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        notificationManager.notify(id,notification)
//    }
//    private fun createNotifactionChenal(){
//        val CHENAL_ID = "101"
//        val CHENAL_NAME="MyApp"
//        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
//
//
//            val channel =
//                NotificationChannel(CHENAL_ID, CHENAL_NAME, NotificationManager.IMPORTANCE_HIGH)
//                    .apply {
//                        lightColor = Color.GREEN
//                        enableLights(true)
//
//                    }
//            val manger = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manger.createNotificationChannel(channel)
//        }
//
//    }

}