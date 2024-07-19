package com.example.notiii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.notiii.databinding.ActivityImageViewBinding

//import com.example.notiii.databinding.ActivityImageViewBinding

class ImageView : AppCompatActivity() {
    private lateinit var binding : ActivityImageViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val image = intent.getStringExtra("imageViews")
        Glide.with(this@ImageView)
            .load(image)
            .into(binding.fullScreenImageView)

    }
}