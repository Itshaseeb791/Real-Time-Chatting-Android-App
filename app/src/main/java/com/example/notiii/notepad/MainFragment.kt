package com.example.notiii.notepad

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notiii.R
import com.example.notiii.MyChatMainActivity
import com.example.notiii.MyDatabase.ListAdpater
import com.example.notiii.MyDatabase.User
import com.example.notiii.MyDatabase.UserViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    lateinit var Mydata: List<User>
    private val adapter1 = ListAdpater()
    private lateinit var chatapp1 : TextView
    var mInterstitialAd: InterstitialAd? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.main10, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyleview1)
        val btn: FloatingActionButton = view.findViewById(R.id.floatingActionButton1)
        val sreach : androidx.appcompat.widget.SearchView =view.findViewById(R.id.seach)
        chatapp1=view.findViewById(R.id.textView4)
        var adRequest = AdRequest.Builder().build()
        MobileAds.initialize(requireContext()) {}
        InterstitialAd.load(requireContext(), "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.toString()?.let { Log.d("log", it) }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("log", "Ad was loaded.")
                mInterstitialAd = interstitialAd
                // Show the ad when it's loaded
                showInterstitialAd()
            }
        })
        chatapp1.setOnLongClickListener(){
            //hererrr
            val intent = Intent(this@MainFragment.requireContext(), MyChatMainActivity::class.java)
            startActivity(intent)
             true
        }


        btn.setOnClickListener() {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
        sreach.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                sreach.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filerList(p0)
                return true
            }

        })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
   //     val adapter = ListAdpater()

        adapter1.setdata(listOf<User>())
//        adapter.setdata(yourListOfUserData)


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter1.setdata(user)
            Mydata = user

        })

        recyclerView.adapter = adapter1
        adapter1.onClickListerner(object : ListAdpater.ClickListener {
            override fun getitem(position: Int) {
                dialog(Mydata[position].Id, Mydata[position].note)
            }

        })


        return view
    }

    private fun filerList(p0: String?) {
        if (p0 != null) {

            if (p0.isNotEmpty()){
                val filterList = mutableListOf<User>()
                for (x in Mydata){
                    if (x.note.contains(p0)){
                        filterList.add(x)
                    }
                }
                if (filterList.isEmpty()){
                    Toast.makeText(requireContext(),"Not Found"
                    ,Toast.LENGTH_SHORT).show()
                    adapter1.setdata(emptyList())
                }
                else{
                    adapter1.setdata(filterList as List<User>)
                }

            }else{
                adapter1.setdata(Mydata)
            }
        }else{
            adapter1.setdata(Mydata)
        }

    }

    private fun dialog(Myid : Int,Mynote : String){
        val dialog = AlertDialog.Builder(requireContext())
        val dialog_view = layoutInflater.inflate(R.layout.dialog,null)
        dialog.setView(dialog_view)
        val diaLog = dialog.create()
        diaLog.setTitle("UpDate Notes Here")
        val Updatenote = dialog_view.findViewById<EditText>(R.id.editTextText2)


        val savebtn = dialog_view.findViewById<Button>(R.id.button2)

        val deletebtn = dialog_view.findViewById<ImageView>(R.id.imageView)
        Updatenote.setText(Mynote)
        diaLog.show()

        deletebtn.setOnClickListener(){
            userViewModel.DeleteData(User(Myid,Mynote))
            diaLog.dismiss()

        }
        savebtn.setOnClickListener(){
            val UpdatedNotes = Updatenote.text.toString()
            if (UpdatedNotes.isNotEmpty()){
               val NewNote=User(Myid,UpdatedNotes)
                userViewModel.Updatadata(NewNote)
                Toast.makeText(requireContext(),"Change Succesfully",
                    Toast.LENGTH_SHORT)
                    .show()
                diaLog.dismiss()
            }
            else{
                Toast.makeText(requireContext(),"Cant save an Empty Note",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }



    }
    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        } else {
            Log.d("log", "The interstitial ad wasn't ready yet.")
        }
    }

}