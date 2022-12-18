package com.zeynepaslierhan.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class zorluk_secActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zorluk_sec)

        auth= FirebaseAuth.getInstance()
    }

    fun basicGame(view: View){
        val intent = Intent(this,BasitLoadingScreenActivity::class.java)
        intent.putExtra("activity","1")
        startActivity(intent)
    }
    fun intermediateGame(view: View){
        val intent = Intent(this,BasitLoadingScreenActivity::class.java)
        intent.putExtra("activity","2")
        startActivity(intent)
    }
    fun hardGame(view: View){
        val intent = Intent(this,BasitLoadingScreenActivity::class.java)
        intent.putExtra("activity","3")
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