package com.example.notiii.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ContentInfoCompat.Flags
import com.example.notiii.R
import com.example.notiii.Homescreen
import com.example.notiii.dataclasses.Notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class NotificationService :Service() {
    private lateinit var dbref: FirebaseAuth
    private lateinit var dbdatabase : FirebaseDatabase
    var id = 5
    override fun onBind(p0: Intent?): IBinder? {
        Log.d("log","i am in service1")
         return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
   dbdatabase= FirebaseDatabase.getInstance()
        dbref=FirebaseAuth.getInstance()
         dbdatabase.getReference("Notification")
            .child(dbref.uid!!)
            .child("MessageNotifivcation")
            .addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot.exists()){
                        val list = arrayListOf<String>()
                        for(x in snapshot.children){
                            val mystring = x.getValue(String::class.java)
                            list.add(mystring!!)
//                          val myobject = x.getValue(Notification::class.java)
//                          Log.d("log","here $myobject")
//                          shownotification(myobject)
                        }
                        if (list[0]=="Unnotified") {


                            shownotification(Notification(list[0], list[1], list[3], list[2]))
                            dbdatabase.getReference("Notification")
                                .child(dbref.uid!!)
                                .child("MessageNotifivcation")
                                .child(list[2])
                                .removeValue()
                        }
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        return START_STICKY

    }
    private fun shownotification(myobject: Notification?) {
        val CHENAL_ID = "101"
        createNotifactionChenal()
        val intent = Intent(this@NotificationService,Homescreen::class.java)
        val pendingIntent = PendingIntent.getActivity(this@NotificationService,
        0,intent,   PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(this,CHENAL_ID)
            .setContentTitle(myobject?.senderName)
            .setSmallIcon(R.drawable.iccon)
            .setContentText(myobject?.message)
//            .setSmallIcon(R.drawable.baseline_camera_alt_24)
            .setPriority(NotificationManager.IMPORTANCE_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
//            .setContentIntent(pendingIntent)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify( id,notification)
        id++
    }
    private fun createNotifactionChenal(){
        val CHENAL_ID = "101"
        val CHENAL_NAME="MyApp"
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {


            val channel =
                NotificationChannel(CHENAL_ID, CHENAL_NAME, NotificationManager.IMPORTANCE_HIGH)
                    .apply {
                        lightColor = Color.GREEN
                        enableLights(true)

                    }
//            val mymanger = getSystemService()
//            val manger = getSystemService() as NotificationManager
//             getSystemService(requireContext(),
//             NotificationManager.BUBBLE_PREFERENCE_ALL).createNotificationChannel(channel)
            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
}