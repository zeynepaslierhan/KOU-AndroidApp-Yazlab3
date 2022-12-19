package com.zeynepaslierhan.memorygame.TekKisilik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.zeynepaslierhan.memorygame.Menu.MainActivity
import com.zeynepaslierhan.memorygame.R
import com.zeynepaslierhan.memorygame.Splash.BasitLoadingScreenActivity

class zorluk_secActivity : AppCompatActivity() {
    private var TAG = "Test"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zorluk_sec)

        auth= FirebaseAuth.getInstance()
    }

    fun basicGame(view: View){

        var sample = intent.getStringExtra("sample").toString()

        val intent2 = Intent(this, BasitLoadingScreenActivity::class.java)
        intent2.putExtra("activity",if (sample == "single") "1" else "4")

        Log.d(TAG,sample)

        startActivity(intent2)
    }
    fun intermediateGame(view: View){

        var sample = intent.getStringExtra("sample").toString()

        val intent2 = Intent(this, BasitLoadingScreenActivity::class.java)
        intent2.putExtra("activity",if (sample == "single") "2" else "5")

        Log.d(TAG,sample)

        startActivity(intent2)
    }
    fun hardGame(view: View){

        var sample = intent.getStringExtra("sample").toString()

        val intent2 = Intent(this, BasitLoadingScreenActivity::class.java)
        intent2.putExtra("activity",if (sample == "single") "3" else "6")

        Log.d(TAG,sample)

        startActivity(intent2)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater= menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.LogOut){

            auth.signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}