package com.zeynepaslierhan.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class oyuncu_secActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyuncu_sec)
    }

    fun single(view: View){
        val intent = Intent(this,zorluk_secActivity::class.java)
        startActivity(intent)
    }
    fun multiplayer(view: View){
        //val intent = Intent(this,zorluk_secActivity::class.java)
        //startActivity(intent)
    }

}