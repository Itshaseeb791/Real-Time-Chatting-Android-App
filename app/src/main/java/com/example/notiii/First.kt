package com.example.notiii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.notiii.R
import com.example.notiii.notepad.MainActivity

class First : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)
        Handler().postDelayed({
            val mainIntent = Intent(this@First, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3500)
    }
}