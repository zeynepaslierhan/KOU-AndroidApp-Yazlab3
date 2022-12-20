package com.zeynepaslierhan.memorygame.Splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import com.zeynepaslierhan.memorygame.R

class BasitLoadingScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basit_loading_screen)

        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val textView = findViewById<TextView>(R.id.textView)

        val activity = intent.getStringExtra("activity").toString()

        progressBar.max = 100
        progressBar.scaleY = 3F
        val anim = ProgressBarAnimation(
            this,progressBar,textView,0F,100F,activity
        )
        anim.duration=3000
        progressBar.animation = anim
    }
}