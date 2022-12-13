package com.zeynepaslierhan.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_oyun_basit.*

private const val TAG = "Basit Oyun Activity"

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyun_basit)

        kart_2_1.setOnClickListener {
            Log.i(TAG, "Button Clicked!")
        }
    }
}