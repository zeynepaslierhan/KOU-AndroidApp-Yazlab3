package com.zeynepaslierhan.memorygame.Splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.zeynepaslierhan.memorygame.R

class BasitLoadingScreenActivity : AppCompatActivity() {

    var progressBar = findViewById<ProgressBar>(R.id.progress_bar)
    var textView = findViewById<TextView>(R.id.textView)
    var button = findViewById<Button>(R.id.StartButton)
    lateinit var activity : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basit_loading_screen)

        activity = intent.getStringExtra("activity").toString()

    }
    fun start(){

        progressBar.max = 100
        progressBar.scaleY = 3F

        button.isVisible = false
        progressBar.isVisible = true

        val anim = ProgressBarAnimation(
            this,progressBar,textView,0F,100F,activity
        )

        anim.duration=3000
        progressBar.animation = anim
    }

}

