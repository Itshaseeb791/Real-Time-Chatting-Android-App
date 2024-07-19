package com.example.notiii

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notiii.R
import com.example.notiii.adapter.UserAdapter
import com.example.notiii.dataclasses.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView


class ChatsFragment : Fragment() {
  private lateinit var dbref : FirebaseDatabase
  private lateinit var dbref1 : FirebaseAuth
  private lateinit var Mypersonaldata : ArrayList<UserData>
  private lateinit var   recycleview : RecyclerView
  private lateinit var  Mydata : ArrayList<UserData>
  private var Mydata1 : UserData?=null
  private var name : String?=null
    val MyFriends = arrayListOf<UserData>()
    private lateinit var  Myadapter : UserAdapter
    private  lateinit  var user : ArrayList<UserData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

         val view =  inflater.inflate(R.layout.fragment_chats, container, false)
         val settingbtn : ImageView = view.findViewById(R.id.imageView9)
        dbref= FirebaseDatabase.getInstance()
        dbref1= FirebaseAuth.getInstance()

        user=arrayListOf(
            UserData("01","01","01"
            ,"01","01","01")
        )
        Myadapter= UserAdapter(requireContext(),user)

        recycleview = view.findViewById(R.id.myrecycleview)
        val mysreach = view.findViewById<SearchView>(R.id.sreachh)
        val ref : ImageView=view.findViewById(R.id.imageView9)



      settingbtn.setOnClickListener(){
          if (Mydata1!=null){
              val intent = Intent(requireContext(),SettingActvity::class.java)
              intent.putExtra("Myimage",Mydata1!!.image)
              intent.putExtra("username",Mydata1!!.userName)
              intent.putExtra("email",Mydata1!!.email)
              intent.putExtra("phone",Mydata1!!.phone)
              intent.putExtra("uerid",Mydata1!!.Userid)
              intent.putExtra("password",Mydata1!!.password)

              startActivity(intent)
          }

      }

        mysreach.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    if (newText.isNotEmpty()) {
                        val mylis = arrayListOf<UserData>()
                        for (obj in Mydata) {
                            if (newText in obj.userName)
                                mylis.add(obj)


                        }

                        if (mylis.isNotEmpty()){
                            Myadapter.setData(mylis)
                            Mypersonaldata=mylis


                        }else{
                            Log.d("log","i am postion1")
                            Toast.makeText(requireContext(),
                            "Most Nearest Result is on screen",Toast.LENGTH_SHORT)
                                .show()

                        }


                    }else{
                        Myadapter.setData(MyFriends)
                    }
                }else{
                    Myadapter.setData(MyFriends)
                }
                return true
            }

        })
//       ref.setOnClickListener(){
//           dbref.getReference("User").addValueEventListener(object : ValueEventListener{
//               override fun onDataChange(snapshot: DataSnapshot) {
//
//                   user.clear()
//                   if (snapshot.exists()){
//                       val temp = arrayListOf<UserData>()
//                       for (x in snapshot.children){
//                           val user1 = x.getValue(UserData::class.java)
//                           if (user1 != null) {
//                               if(user1.Userid!=dbref1.uid) {
//
//
//                                   temp.add(user1)
//                               }
//                           }
//                           user=temp
//                           Mydata=temp
//
//
//                       }
//                       Log.d("log","i am main object")
//                       Myadapter.setData(temp)
//                       recycleview.adapter=Myadapter
////                      Myadapter.setData(user)
//                       Myadapter.onClickListerner(object : UserAdapter.ClickListener {
//                           override fun getitem(position: Int) {
//                               val intent=Intent(requireContext(),MainchatActivity::class.java)
//                               intent.putExtra("UserName",user[position].userName)
//                               intent.putExtra("image",user[position].image)
//                               intent.putExtra("iduid",user[position].Userid)
//                               startActivity(intent)
//                           }
//
//                       })
//
//                   }
//               }
//
//
//               override fun onCancelled(error: DatabaseError) {
//
//               }
//
//           })
//       }
        recycleview.setHasFixedSize(true)
        recycleview.layoutManager=  GridLayoutManager(requireContext(),2)

        dbref.getReference("User").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                user.clear()
                 if (snapshot.exists()){
                       val temp = arrayListOf<UserData>()
                     for (x in snapshot.children){
                         val user1 = x.getValue(UserData::class.java)
                         if (user1 != null) {
                             if(user1.Userid!=dbref1.uid) {


                                 temp.add(user1)
                             }else{
                                 Mydata1=user1
                                 name=user1.userName
                                 val sharedPrefs = requireActivity().getSharedPreferences("mypre2", Context.MODE_PRIVATE)
                                 sharedPrefs.edit().putString("userName", Mydata1!!.userName).apply()
                                 sharedPrefs.edit().putString("photo", Mydata1!!.image).apply()

                             }
                         }
//                         user=temp
                         Mydata=temp

                     }
                     Log.d("log","i am main object")
//                     Myadapter.setData(temp)
//                     recycleview.layoutManager = LinearLayoutManager(requireContext())
//                     val admobNativeAdAdapter =
//                         AdmobNativeAdAdapter.Builder
//                         .with(
//                             "ca-app-pub-3940256099942544/2247696110",  //Create a native ad id from admob console
//                             Myadapter,  //The adapter you would normally set to your recyClerView
//                             "medium" //Set it with "small","medium" or "custom"
//                         )
//                         .adItemIterval(2) //native ad repeating interval in the recyclerview
//                         .build()
//                     recycleview.adapter = admobNativeAdAdapter
                     recycleview.adapter=Myadapter
                     load(view)

//                      Myadapter.setData(user)
                     Myadapter.onClickListerner(object : UserAdapter.ClickListener {
                         override fun getitem(position: Int) {
                           val intent=Intent(requireContext(),MainchatActivity::class.java)
                             intent.putExtra("UserName",Mypersonaldata[position].userName)
                             intent.putExtra("image",Mypersonaldata[position].image)
                             intent.putExtra("iduid",Mypersonaldata[position].Userid)
                             intent.putExtra("name",name)
                             startActivity(intent)
                         }

                     })

                 }
            }


            override fun onCancelled(error: DatabaseError) {

            }

        })

        dbref.getReference("Friendss").child(dbref1.uid!!)
            .child("Friend").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                 if (snapshot.exists()){
                     MyFriends.clear()
                     for (x in snapshot.children)
                     {
                        val ibjectuser= x.getValue(UserData::class.java)
                         if (ibjectuser != null) {
                             MyFriends.add(ibjectuser)
                         }

                     }
                     Myadapter.setData(MyFriends)
                     Mypersonaldata=MyFriends
                     val  i=3
                 }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
//        dbref= FirebaseAuth.getInstance()
//        dbdatabase= FirebaseDatabase.getInstance()

//         dbref.getReference("Notification")
//            .child(dbref1.uid!!)
//            .child("MessageNotifivcation")
//            .addChildEventListener(object : ChildEventListener{
//                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    if (snapshot.exists()){
//                        val list = arrayListOf<String>()
//                        for(x in snapshot.children){
//                            val mystring = x.getValue(String::class.java)
//                            list.add(mystring!!)
////                          val myobject = x.getValue(Notification::class.java)
////                          Log.d("log","here $myobject")
////                          shownotification(myobject)
//                        }
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
//                    Toast.makeText(requireContext(),error.toString()
//                        ,Toast.LENGTH_SHORT).show()
//                }
//
//            })
           return view
       }

    override fun onResume() {

        dbref.getReference("Friendss").child(dbref1.uid!!)
            .child("Friend").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        MyFriends.clear()
                        for (x in snapshot.children)
                        {
                            val ibjectuser= x.getValue(UserData::class.java)
                            if (ibjectuser != null) {
                                MyFriends.add(ibjectuser)
                                Log.d("log",ibjectuser.password)
                            }

                        }
                        Myadapter.setData(MyFriends)
                        Mypersonaldata=MyFriends

                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        super.onResume()
    }
//    private fun shownotification(myobject: Notification?) {
//        val id = 5
//        val CHENAL_ID = "101"
//        createNotifactionChenal()
//        val notification = NotificationCompat.Builder(requireContext(),CHENAL_ID)
//            .setContentTitle(myobject?.senderName)
//            .setSmallIcon(R.drawable.iccon)
//            .setContentText(myobject?.message)
////            .setSmallIcon(R.drawable.baseline_camera_alt_24)
//            .setPriority(NotificationManager.IMPORTANCE_MAX)
////            .setContentIntent(pendingIntent)
//            .build()
//        val notificationManager = NotificationManagerCompat.from(requireContext())
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
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
////            val mymanger = getSystemService()
////            val manger = getSystemService() as NotificationManager
////             getSystemService(requireContext(),
////             NotificationManager.BUBBLE_PREFERENCE_ALL).createNotificationChannel(channel)
//            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//
//    }
private fun load(view : View) {
    // Initialize the AdLoader.Builder
    val adBuilder: AdLoader.Builder = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")

    // Define a callback for when the native ad is loaded
    adBuilder.forNativeAd { nativeAd ->
        val layout: FrameLayout = view.findViewById(R.id.nativead1)
        val adView2: NativeAdView = layoutInflater.inflate(R.layout.nativelayout, null) as NativeAdView
        populateNativeAdView(nativeAd, adView2)
        layout.removeAllViews() // Remove any previous views
        layout.addView(adView2)
//        nativeAd.adView = adView2
    }

    // Create an AdLoader and set an AdListener for handling ad events
    val adLoader = adBuilder.withAdListener(object : AdListener() {
        override fun onAdClicked() {
            super.onAdClicked()
            // Handle ad click event
        }

        override fun onAdFailedToLoad(p0: LoadAdError) {
            super.onAdFailedToLoad(p0)
            Toast.makeText(requireContext(), "Failed to load ad: ${p0.message}", Toast.LENGTH_SHORT).show()
        }

        // Add other ad event callbacks as needed
    }).build()

    // Load the ad with an AdRequest
    adLoader.loadAd(com.google.android.gms.ads.AdRequest.Builder().build())

    com.google.android.gms.ads.AdRequest.Builder().build()
}
private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
    // Set the media view.
    adView.mediaView = adView.findViewById(R.id.ad_media)

    // Set other ad assets.
    adView.headlineView = adView.findViewById(R.id.ad_headline)
    adView.bodyView = adView.findViewById(R.id.ad_body)
    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
    adView.iconView = adView.findViewById(R.id.ad_app_icon)
    adView.priceView = adView.findViewById(R.id.ad_price)
    adView.starRatingView = adView.findViewById(R.id.ad_stars)
    adView.storeView = adView.findViewById(R.id.ad_store)
    adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

    // Smart cast issue resolved using safe cast (as?)
    (adView.headlineView as? TextView)?.text = nativeAd.headline
    adView.mediaView?.mediaContent = nativeAd.mediaContent

    if (nativeAd.body == null) {
        adView.bodyView?.visibility = View.INVISIBLE
    } else {
        adView.bodyView?.visibility = View.VISIBLE
        (adView.bodyView as? TextView)?.text = nativeAd.body
    }

    if (nativeAd.callToAction == null) {
        adView.callToActionView?.visibility = View.INVISIBLE
    } else {
        adView.callToActionView?.visibility = View.VISIBLE
        (adView.callToActionView as? Button)?.text = nativeAd.callToAction
    }

    if (nativeAd.icon == null) {
        adView.iconView?.visibility = View.GONE
    } else {
        (adView.iconView as? ImageView)?.setImageDrawable(nativeAd.icon?.drawable)
        adView.iconView?.visibility = View.VISIBLE
    }

    if (nativeAd.price == null) {
        adView.priceView?.visibility = View.INVISIBLE
    } else {
        adView.priceView?.visibility = View.VISIBLE
        (adView.priceView as? TextView)?.text = nativeAd.price
    }

    if (nativeAd.store == null) {
        adView.storeView?.visibility = View.INVISIBLE
    } else {
        adView.storeView?.visibility = View.VISIBLE
        (adView.storeView as? TextView)?.text = nativeAd.store
    }

    if (nativeAd.starRating == null) {
        adView.starRatingView?.visibility = View.INVISIBLE
    } else {
        (adView.starRatingView as? RatingBar)?.rating = nativeAd.starRating?.toFloat() ?: 0f
        adView.starRatingView?.visibility = View.VISIBLE
    }

    if (nativeAd.advertiser == null) {
        adView.advertiserView?.visibility = View.INVISIBLE
    } else {
        (adView.advertiserView as? TextView)?.text = nativeAd.advertiser
        adView.advertiserView?.visibility = View.VISIBLE
    }

    // This method tells the Google Mobile Ads SDK that you have finished populating your
    // native ad view with this native ad.
    adView.setNativeAd(nativeAd)
}



}