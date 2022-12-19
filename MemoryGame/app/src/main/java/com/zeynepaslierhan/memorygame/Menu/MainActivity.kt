package com.zeynepaslierhan.memorygame.Menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.zeynepaslierhan.memorygame.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if(user!=null){

            val intent =Intent(this, oyuncu_secActivity::class.java)

            val usermail = user.email.toString()
            Toast.makeText(this,"Welcome: ${usermail}",Toast.LENGTH_LONG).show()

            startActivity(intent)
            finish()
        }
    }

    fun login(view: View){

        val email = EmailText.text.toString()
        val password = PasswordText.text.toString()


        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){

                val user = auth.currentUser?.email.toString()

                Toast.makeText(this,"Welcome: ${user}",Toast.LENGTH_LONG).show()

                val intent = Intent(this, oyuncu_secActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

    fun register(view: View){

        val email = EmailText.text.toString()
        val password = PasswordText.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){

                val user = auth.currentUser?.email.toString()

                Toast.makeText(this,"Register is successfull: ${user}",Toast.LENGTH_LONG).show()

                val intent = Intent(this, oyuncu_secActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }
}