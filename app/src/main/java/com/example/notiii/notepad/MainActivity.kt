package com.example.notiii.notepad

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.notiii.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomsheet.BottomSheetDialog

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        var mInterstitialAd: InterstitialAd? = null
//        var adRequest = AdRequest.Builder().build()
//        MobileAds.initialize(this) {}
//        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                adError?.toString()?.let { Log.d( "log", it) }
//                mInterstitialAd = null
//            }
//
//            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                Log.d( "log", "Ad was loaded.")
//                mInterstitialAd = interstitialAd
//            }
//        })
//        if (mInterstitialAd != null) {
//            mInterstitialAd?.show(this)
//        }else {
//            Log.d("log", "The interstitial ad wasn't ready yet.")
//        }
//    }
//
//
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        finishAffinity()
//    }
//}
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }



    override fun onBackPressed() {
//        super.onBackPressed()

     val dialog1 = AlertDialog.Builder(this@MainActivity)

        val dialog =  BottomSheetDialog(this@MainActivity)
        val dialog_view = LayoutInflater.from(this@MainActivity).inflate(R.layout.bannerexit,null)
    dialog.setContentView(dialog_view)

        lateinit var mAdView : AdView
        MobileAds.initialize(this) {}

        mAdView = dialog_view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
       val maindialog = dialog.create()
        dialog.show()
         val exit : Button = dialog_view.findViewById(R.id.button8)
        val canel : Button = dialog_view.findViewById(R.id.button9)
        exit.setOnClickListener(){
            finishAffinity()
        }
        canel.setOnClickListener(){
            dialog.dismiss()
        }

    }
}
