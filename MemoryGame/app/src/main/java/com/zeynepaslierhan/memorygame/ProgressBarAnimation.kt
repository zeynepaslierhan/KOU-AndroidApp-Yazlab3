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
) :Animation()

{
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value = from + (to - from) * interpolatedTime
        progressBar.progress = value.toInt()
        textView.text = "Oyun Yükleniyor  ${value.toInt()} %"

        if (value == to){
            context.startActivity(Intent(context,OyunBasitActivity::class.java))
        }

    }
}