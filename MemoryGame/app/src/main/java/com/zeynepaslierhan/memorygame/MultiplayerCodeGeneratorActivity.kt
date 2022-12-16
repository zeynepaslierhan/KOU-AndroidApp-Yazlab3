package com.zeynepaslierhan.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

var CodeMaker= true
var code = "null"
var codeFound = false
var checkTemp= true
var keyValue : String ="null"

class MultiplayerCodeGeneratorActivity : AppCompatActivity() {

    lateinit var TextView : TextView
    lateinit var codeEdt : EditText
    lateinit var createCodeBtn : Button
    lateinit var joinCodeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplayer_code_generator)

        TextView = findViewById(R.id.TextView)
        codeEdt = findViewById(R.id.CodeText)
        createCodeBtn = findViewById(R.id.button_odaKur)
        joinCodeBtn = findViewById(R.id.button_katıl)

        createCodeBtn.setOnClickListener{
            code = "null"
            codeFound = false
            checkTemp = true
            keyValue="null"
            code=codeEdt.text.toString()

            createCodeBtn.visibility = View.GONE
            TextView.visibility = View.GONE
            codeEdt.visibility = View.GONE
            joinCodeBtn.visibility = View.GONE

            if(code !=null && code != ""){
                CodeMaker = true
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var check = kullanılabilir(snapshot, code)
                        Handler().postDelayed({
                            if(check==true){
                                createCodeBtn.visibility = View.VISIBLE
                                TextView.visibility = View.VISIBLE
                                codeEdt.visibility = View.VISIBLE
                                joinCodeBtn.visibility = View.VISIBLE
                            }else{
                                FirebaseDatabase.getInstance().reference.child("codes").push().setValue(code)
                                kullanılabilir(snapshot,code)
                                check = false
                                Handler().postDelayed({
                                    onaylandı()
                                    Toast.makeText(this@MultiplayerCodeGeneratorActivity,"Lütfen Geriye Gitmeyin",Toast.LENGTH_LONG).show()
                                },300)
                            }
                        },2000)
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            }else{

                createCodeBtn.visibility = View.VISIBLE
                TextView.visibility = View.VISIBLE
                codeEdt.visibility = View.VISIBLE
                joinCodeBtn.visibility = View.VISIBLE
                Toast.makeText(this,"Lütfen Geçerli Kod Giriniz",Toast.LENGTH_LONG).show()
            }

        }

        joinCodeBtn.setOnClickListener {
            code = "null"
            codeFound = false
            checkTemp = true
            keyValue = "null"
            code = codeEdt.text.toString()

            if (code != "null" && code != "") {

                createCodeBtn.visibility = View.GONE
                TextView.visibility = View.GONE
                codeEdt.visibility = View.GONE
                joinCodeBtn.visibility = View.GONE

                CodeMaker =false

                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var data : Boolean = kullanılabilir(snapshot,code)
                        Handler().postDelayed({
                            if(data==true){
                                codeFound = true
                                onaylandı()
                                createCodeBtn.visibility = View.VISIBLE
                                TextView.visibility = View.VISIBLE
                                codeEdt.visibility = View.VISIBLE
                                joinCodeBtn.visibility = View.VISIBLE
                            }
                            else{
                                createCodeBtn.visibility = View.VISIBLE
                                TextView.visibility = View.VISIBLE
                                codeEdt.visibility = View.VISIBLE
                                joinCodeBtn.visibility = View.VISIBLE

                                Toast.makeText(this@MultiplayerCodeGeneratorActivity,"Geçersiz Kod",Toast.LENGTH_LONG).show()

                            }
                        },2000)
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })

            } else {
                Toast.makeText(this, "Lütfen Geçerli Kod Giriniz", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun onaylandı(){

        var intent= Intent(this,MultiplayerGameActivity::class.java)
        startActivity(intent)
        createCodeBtn.visibility = View.VISIBLE
        TextView.visibility = View.VISIBLE
        codeEdt.visibility = View.VISIBLE
        joinCodeBtn.visibility = View.VISIBLE
    }

    fun kullanılabilir(snapshot: DataSnapshot, code: String): Boolean {
        var data = snapshot.children
        data.forEach{
            var value = it.getValue().toString()
            if(value == code){
                keyValue = it.key.toString()
                return true
            }
        }
        return false
    }
}