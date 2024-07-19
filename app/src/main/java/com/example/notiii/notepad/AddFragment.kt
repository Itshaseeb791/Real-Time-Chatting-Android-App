package com.example.notiii.notepad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notiii.R
import com.example.notiii.MyDatabase.User
import com.example.notiii.MyDatabase.UserViewModel

class AddFragment : Fragment() {

  private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        val note : EditText = view.findViewById(R.id.editTextText)
        val savebtn : TextView= view.findViewById(R.id.button)
        val backbtn : ImageView = view.findViewById(R.id.imageView10)
        userViewModel=ViewModelProvider(this).get(UserViewModel::class.java)

        savebtn.setOnClickListener(){
            val message = note.text.toString()
//            var messagecgecking : String = ""
//            val messagLen=message.length
//           for(x in 0 until messagLen){
//               messagecgecking="$messagecgecking\n"
//           }

            if (message.isNotEmpty()&&message!=null&& message.trim().isNotEmpty()){
              val MyObject =User(0,message)
                userViewModel.addUser(MyObject)
                Toast.makeText(requireContext(),"SuccesFully Saved"
                    ,Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_mainFragment)
            }
            else{
                Toast.makeText(requireContext(),"Can't Save An empty Text"
                ,Toast.LENGTH_SHORT).show()
            }
        }
        backbtn.setOnClickListener(){
            findNavController().navigate(R.id.action_addFragment_to_mainFragment)
        }

  return view
    }


}