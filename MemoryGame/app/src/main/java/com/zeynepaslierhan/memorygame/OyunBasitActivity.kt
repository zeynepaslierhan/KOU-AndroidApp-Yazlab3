package com.zeynepaslierhan.memorygame

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.storage.FirebaseStorage

import kotlinx.android.synthetic.main.activity_oyunbasit.*
import java.io.File

private const val TAG = "Basit Test";

class OyunBasitActivity : AppCompatActivity() {

    private lateinit var views: List<ImageView>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    private var puan : Int = 0

    private var MPbacground: MediaPlayer? = null
    private var MPmatch: MediaPlayer? = null
    private var MPwin: MediaPlayer? = null
    private var MPlost: MediaPlayer? = null

    private var matchCounter : Int?=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyunbasit)

        if (MPbacground == null) {
            MPbacground = MediaPlayer.create(this, R.raw.background)
            MPbacground!!.start()
        }
        if (MPmatch == null) {
            MPmatch = MediaPlayer.create(this, R.raw.card_match)
        }
        if (MPwin == null) {
            MPwin = MediaPlayer.create(this, R.raw.win_congrats)
        }
        if (MPlost == null) {
            MPlost = MediaPlayer.create(this, R.raw.time_over)
        }

        val images = mutableListOf(R.drawable.gryffindor1,R.drawable.hufflepuff1)

        // her img'i 2 kez ekleyerek çiftler oluşturuyoruz.
        images.addAll(images)

        // sırayı randomize ediyor.
        images.shuffle()

        val storageRef = FirebaseStorage.getInstance().reference.child("cards/gryffindor1.png")
        val localfile = File.createTempFile("tempImage","png")
        storageRef.getFile(localfile).addOnSuccessListener{
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)

            // Bitmapi atama ->     imageid.setImageBitmap(bitmap)

        }.addOnFailureListener{
            Log.d(TAG,"Resim alınamadı")
        }

        views = listOf(imageView1, imageView2, imageView3, imageView4)

        cards = views.indices.map { index ->
            MemoryCard(images[index])
        }

        views.forEachIndexed { index, view ->
            view.setOnClickListener {

                // modelleri güncelleme
                updateModels(index)

                // UI güncelleme
                updateViews()

            }
        }

        object : CountDownTimer(45000,1000) {
            override fun onTick(time: Long) {
                if(matchCounter == 2)
                {
                    sayac.text = "Tebrikler!!!"
                }else{
                    sayac.text = "Kalan Süre: ${time / 1000}"
                }
            }

            override fun onFinish() {

                if(matchCounter != 2)
                {
                    MPlost?.start()

                    sayac.text = "Süre Bitti!"
                    imageView1.isVisible=false;
                    imageView2.isVisible=false;
                    imageView3.isVisible=false;
                    imageView4.isVisible=false;
                }

                val handler = Handler()
                handler.postDelayed({ // Do something after 5s = 5000ms
                    val intent = Intent(this@OyunBasitActivity,zorluk_secActivity::class.java)
                    startActivity(intent)

                    MPbacground?.stop()
                    finish()
                }, 5000)
            }
        }.start()
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->

            val view = views[index]
            if (card.isMatched) {
                view.alpha = 0.3f
            }
            view.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.kart)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]

        // hata kontrolü
        if(card.isFaceUp) {
            Toast.makeText(this, "Geçersiz Hareket!", Toast.LENGTH_SHORT).show();
            return
        }

                    // üç durum olacak
        // öncesinde 0 kart çevrilmiş -> seçilen kartı çevir
        // öncesinde 1 kart çevrilmiş -> seçilen kartı çevir + aynı olup olmamasını kontrol et
        // öncesinde 2 kart çevrilmiş -> seçilmiş kartları ters çevir + seçili kartı çevir.

        if (indexOfSingleSelectedCard == null) {
            // 0 ya da 2 seçili kart varsa
            restoreCards()
            MPbacground?.start()
            indexOfSingleSelectedCard = position
        } else {
            // 1 kart seçiliyse
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }

        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier) {

            matchCounter = matchCounter?.plus(1)

            Toast.makeText(this, "Eşleşme Bulundu!", Toast.LENGTH_SHORT).show();

            cards[position1].isMatched = true
            cards[position2].isMatched = true

            puan = puan + 10
            puanTextView.text = " Puan: ${puan.toString()}"


            if(matchCounter == 2){
                MPwin?.start()

                val handler = Handler()
                handler.postDelayed({ // Do something after 8s = 8000ms
                    val intent = Intent(this@OyunBasitActivity,zorluk_secActivity::class.java)
                    startActivity(intent)

                    MPbacground?.stop()
                    MPmatch?.stop()
                    MPwin?.stop()
                    finish()
                }, 8000)
            }else{
                MPmatch?.start()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        MPbacground?.stop()
        MPmatch?.stop()
        MPwin?.stop()
    }
}
