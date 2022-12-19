package com.zeynepaslierhan.memorygame.Menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.zeynepaslierhan.memorygame.Kartlar.CardsFromFirebase
import com.zeynepaslierhan.memorygame.R
import com.zeynepaslierhan.memorygame.TekKisilik.zorluk_secActivity

class oyuncu_secActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    var cards = CardsFromFirebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyuncu_sec)

        cards.cardSetting()

        auth= FirebaseAuth.getInstance()

    }

    fun single(view: View){
        val intent = Intent(this, zorluk_secActivity::class.java)
        intent.putExtra("sample", "single")

        startActivity(intent)
    }
    fun multiplayer(view: View){
        val intent = Intent(this,zorluk_secActivity::class.java)
        intent.putExtra("sample", "multi")

        startActivity(intent)
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