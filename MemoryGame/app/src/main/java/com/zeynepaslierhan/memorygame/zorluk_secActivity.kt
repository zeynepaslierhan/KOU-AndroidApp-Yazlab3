package com.zeynepaslierhan.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class zorluk_secActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zorluk_sec)
    }

    fun basicGame(view: View){
        val intent = Intent(this,OyunBasitActivity::class.java)
        startActivity(intent)
    }
    fun intermediateGame(view: View){
        val intent = Intent(this,OyunOrtaActivity::class.java)
        startActivity(intent)
    }
    fun hardGame(view: View){
        val intent = Intent(this,OyunZorActivity::class.java)
        startActivity(intent)
    }
}