package com.example.notiii

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.tv.AdRequest
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.notiii.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView


class SettingActvity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_actvity)
        sharedPreferences=getSharedPreferences("mypre2", MODE_PRIVATE)
        val Name = intent.getStringExtra("username")
        val image = intent.getStringExtra("Myimage")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        val usid = intent.getStringExtra("uerid")
        val mypasi = intent.getStringExtra("password")
//        val sharedPreferences : SharedPreferences =getSharedPreferences("mypre2",
//            AppCompatActivity.MODE_PRIVATE
//        )

//            sharedPreferences.edit().putString("userName",Name).apply()
//            sharedPreferences.edit().putString("photo",image).apply()

        val username: TextView = findViewById(R.id.textView20)
        val emailView: TextView = findViewById(R.id.textView19)
        val imageView: ImageView = findViewById(R.id.imageviewa)
        username.text = Name
        emailView.text = email
        Glide.with(this@SettingActvity)
            .load(image)
            .error(R.drawable.baseline_account_circle_24)
            .into(imageView)
        val logout : LinearLayout = findViewById(R.id.linearLayout9)
        val accountSetting : LinearLayout = findViewById(R.id.linearLayout6)
        val switch : Switch = findViewById(R.id.switch1)
        val existing_value = sharedPreferences.getString("Myvalue","true")
        switch.isChecked = when(existing_value){
            "true"->true
            "false"->false
            else->true
        }
        load()
        switch.setOnCheckedChangeListener { _, isChecked ->
             val message = isChecked.toString()
            sharedPreferences.edit().putString("Myvalue",message).apply()
            if (message =="true"){
                Toast.makeText(this@SettingActvity,"Your Notification Has been Enabled",
                    Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@SettingActvity,"Your Notification Has been Disbale",
                    Toast.LENGTH_SHORT).show()
            }
        }
        val chatswallper: LinearLayout = findViewById(R.id.linearLayout8)
        chatswallper.setOnClickListener(){
            val dialog = AlertDialog.Builder(this@SettingActvity)
            dialog.setTitle("Updating Chats Wallpaper")
            dialog.setMessage("Are you sure you want to update your wallpaper")
            dialog.setPositiveButton("Yes",object :DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                     getimage()
                }

            }).setNegativeButton("Cancel",object :DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }

            }).show()
        }
        logout.setOnClickListener(){
            val intent= Intent(this@SettingActvity,MyChatMainActivity::class.java)
            intent.putExtra("email","mu")
            intent.putExtra("pass","mu")
            startActivity(intent)
        }
        accountSetting.setOnClickListener(){
            val intent = Intent( this@SettingActvity,AccountSetting::class.java)
            intent.putExtra("Myimage",image)
            intent.putExtra("username",Name )
            intent.putExtra("email",email )
            intent.putExtra("phone",phone)
            intent.putExtra("uerid",usid )
            intent.putExtra("password",mypasi)
            startActivity(intent)
        }


    }

    private fun getimage() {
         val intent = Intent()
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        intent.type="image/*"
        startActivityForResult(intent,52)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            if (data.data!=null){
                val mywallpaperuri=data.data.toString()
                sharedPreferences.edit().putString("Mywallpaper",mywallpaperuri).apply()
                Toast.makeText(this@SettingActvity,"Selected",Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            Toast.makeText(this@SettingActvity,"Nothing Selected",Toast.LENGTH_SHORT)
                .show()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@SettingActvity,Homescreen::class.java))
    }
//    private fun  loadAdd(){
//        val adLoader = AdLoader.Builder(this@SettingActvity, "ca-app-pub-3940256099942544/2247696110")
//            .forNativeAd { ad : NativeAd ->
//                // Show the ad.
//            }
//            .withAdListener(object : AdListener() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    // Handle the failure by logging, altering the UI, and so on.
//                }
//            })
//            .withNativeAdOptions(
//                NativeAdOptions.Builder()
//                // Methods in the NativeAdOptions.Builder class can be
//                // used here to specify individual options settings.
//                .build())
//            .build()
        // Function to load a native ad
//        private fun loadNativeAd() {
//            val adLoader = AdLoader.Builder(this@SettingActvity, "ca-app-pub-3940256099942544/2247696110")
//                .forNativeAd { nativeAd ->
//                    // Native ad loaded successfully, now populate the ad view
//                    val yourNativeAdView = findViewById<FrameLayout>(R.id.nativead)
//                    populateNativeAdView(nativeAd, yourNativeAdView)
//                }
//                .build()
//
//            adLoader.loadAd(AdRequest.Builder().build())
//        }


//    }
private fun load() {
    // Initialize the AdLoader.Builder
    val adBuilder: AdLoader.Builder = AdLoader.Builder(this@SettingActvity, "ca-app-pub-3940256099942544/2247696110")

    // Define a callback for when the native ad is loaded
    adBuilder.forNativeAd { nativeAd ->
        val layout: FrameLayout = findViewById(R.id.nativead)
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
            Toast.makeText(this@SettingActvity, "Failed to load ad: ${p0.message}", Toast.LENGTH_SHORT).show()
        }

        // Add other ad event callbacks as needed
    }).build()

    // Load the ad with an AdRequest
    adLoader.loadAd(com.google.android.gms.ads.AdRequest.Builder().build())

    com.google.android.gms.ads.AdRequest.Builder().build()
}
//    private fun load(){
//        val adBuilder : AdLoader.Builder = AdLoader.Builder(this@SettingActvity,"ca-app-pub-3940256099942544/2247696110")
//        adBuilder.forNativeAd(object : NativeAd.OnNativeAdLoadedListener{
//            override fun onNativeAdLoaded(p0: NativeAd) {
//                 val mnativeAd = p0
//                val layout : FrameLayout = findViewById(R.id.nativead)
//                val adview : NativeAdView = layoutInflater.inflate(R.layout.nativelayout,null) as NativeAdView
//                populateNativeAdView(mnativeAd,adview)
//            }
//
//        })
//    val adloader = adBuilder.withAdListener(object :AdListener(){
//        override fun onAdClicked() {
//            super.onAdClicked()
//        }
//
//        override fun onAdFailedToLoad(p0: LoadAdError) {
//            super.onAdFailedToLoad(p0)
//            Toast.makeText(this@SettingActvity,"Fail to load Text",
//            Toast.LENGTH_SHORT).show()
//        }
//    }).build()
//    adloader.loadAd(AdRequest.Builder().build)
//    }
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