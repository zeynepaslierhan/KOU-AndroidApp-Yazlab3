package com.zeynepaslierhan.memorygame

import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import android.widget.TextView

class ProgressBarAnimation (

    var context: Context,
    var progressBar: ProgressBar,
    var textView: TextView,
    var from: Float,
    var to: Float,
    var activity: String,
) :Animation()

{
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value = from + (to - from) * interpolatedTime
        progressBar.progress = value.toInt()
        textView.text = "Oyun Yükleniyor  ${value.toInt()} %"

        if (value == to && activity == "1"){
            context.startActivity(Intent(context,OyunBasitActivity::class.java))
        }

        if (value == to && activity == "2"){
            context.startActivity(Intent(context,OyunOrtaActivity::class.java))
        }

        if (value == to && activity == "3"){
            context.startActivity(Intent(context,OyunZorActivity::class.java))
        }
    }
}