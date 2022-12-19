package com.zeynepaslierhan.memorygame.Menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.zeynepaslierhan.memorygame.R
import com.zeynepaslierhan.memorygame.TekKisilik.zorluk_secActivity

class oyuncu_secActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyuncu_sec)

        auth= FirebaseAuth.getInstance()

    }

    fun single(view: View){
        val intent = Intent(this, zorluk_secActivity::class.java)
        startActivity(intent)
    }
    fun multiplayer(view: View){
        //val intent = Intent(this,zorluk_secActivity::class.java)
        //startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater= menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.LogOut){

            auth.signOut()

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}