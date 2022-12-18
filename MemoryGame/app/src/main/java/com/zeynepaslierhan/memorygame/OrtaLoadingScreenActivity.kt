package com.zeynepaslierhan.memorygame

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView


class OrtaLoadingScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orta_loading_screen)

        val ortaprogressBar = findViewById<ProgressBar>(R.id.ortaprogress_bar)
        val ortatextView = findViewById<TextView>(R.id.ortatextView)
        val activity = 2;

        ortaprogressBar.max = 100
        ortaprogressBar.scaleY = 3F
        val anim = ProgressBarAnimation(
            this,ortaprogressBar,ortatextView,0F,100F,activity
        )
        anim.duration=8000
        ortaprogressBar.animation = anim
    }
}