package com.zeynepaslierhan.memorygame.Multi

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.zeynepaslierhan.memorygame.Kartlar.MemoryCard
import com.zeynepaslierhan.memorygame.R
import com.zeynepaslierhan.memorygame.TekKisilik.zorluk_secActivity
import kotlinx.android.synthetic.main.activity_multi_basit.*


class MultiBasitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_basit)

        // Sayfa Yapısı
        musicSetting()
        timer()

        val images = mutableListOf(R.drawable.gryffindor1,R.drawable.hufflepuff4)

        // her img'i 2 kez ekleyerek çiftler oluşturuyoruz.
        images.addAll(images)

        // sırayı randomize ediyor.
        images.shuffle()

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
    }

    var player1 = true
    private var puan : Int = 0

    var player2 = false
    private var puan2 : Int = 0

    private lateinit var views: List<ImageView>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    // Müzik Ayarları

    private var MPbacground: MediaPlayer? = null
    private var MPmatch: MediaPlayer? = null
    private var MPwin: MediaPlayer? = null
    private var MPlost: MediaPlayer? = null

    fun musicSetting(){
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
    }


    // Sayaç ve kalan süre hesapları

    private var KalanSüre : Long ?=null

    fun timer(){

        object : CountDownTimer(45000,1000) {
            override fun onTick(time: Long) {
                KalanSüre= time
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
                    val intent = Intent(this@MultiBasitActivity, zorluk_secActivity::class.java)
                    startActivity(intent)

                    MPbacground?.stop()
                    finish()
                }, 5000)
            }
        }.start()
    }

    // Tüm kartların eşleşmesi

    private var matchCounter : Int?=0

    fun matchedController(matchedCards:Int){
        if(matchCounter == matchedCards){
            MPwin?.start()

            val handler = Handler()
            handler.postDelayed({ // Do something after 8s = 8000ms
                val intent = Intent(this@MultiBasitActivity, zorluk_secActivity::class.java)
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

            if (player1){
                puan = puan + 10
                puanTextView.text = " Puan: ${puan.toString()}"
            }else{
                puan2 = puan2 + 10
                puanTextView2.text = " Puan: ${puan2.toString()}"
            }


            matchedController(2)
        }
        else{
            if(player1) {
                player1 = false
                player2 = true
            }else{
                player1 = true
                player2 = false
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