package com.zeynepaslierhan.memorygame

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_oyunbasit.*
import kotlinx.android.synthetic.main.activity_oyunorta.*
import kotlinx.android.synthetic.main.activity_oyunorta.imageView1
import kotlinx.android.synthetic.main.activity_oyunorta.imageView2
import kotlinx.android.synthetic.main.activity_oyunorta.imageView3
import kotlinx.android.synthetic.main.activity_oyunorta.imageView4
import kotlinx.android.synthetic.main.activity_oyunorta.puanTextView
import kotlinx.android.synthetic.main.activity_oyunorta.sayac

class OyunOrtaActivity : AppCompatActivity() {

    private lateinit var views: List<ImageView>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    private var puan : Int = 0

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
                    imageView5.isVisible=false;
                    imageView6.isVisible=false;
                    imageView7.isVisible=false;
                    imageView8.isVisible=false;
                    imageView9.isVisible=false;
                    imageView10.isVisible=false;
                    imageView11.isVisible=false;
                    imageView12.isVisible=false;
                    imageView13.isVisible=false;
                    imageView14.isVisible=false;
                    imageView15.isVisible=false;
                    imageView16.isVisible=false;
                }

                val handler = Handler()
                handler.postDelayed({ // Do something after 5s = 5000ms
                    val intent = Intent(this@OyunOrtaActivity,zorluk_secActivity::class.java)
                    startActivity(intent)

                    MPbacground?.stop()
                    finish()
                }, 5000)
            }
        }.start()
    }


    // Kart Ayarları

    private lateinit var CardsBacground : Bitmap
    private lateinit var card1 : Bitmap
    private lateinit var card2 : Bitmap


    // Tüm kartların eşleşmesi

    private var matchCounter : Int?=0

    fun matchedController(matchedCards:Int){
        if(matchCounter == matchedCards){
            MPwin?.start()

            val handler = Handler()
            handler.postDelayed({ // Do something after 8s = 8000ms
                val intent = Intent(this@OyunOrtaActivity,zorluk_secActivity::class.java)
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyunorta)

        // Sayfa Yapısı
        musicSetting()
        timer()
        CardsFromFirebase().cardSetting()

        CardsBacground = CardsFromFirebase().decoded_cardBackground
        card1 = CardsFromFirebase().decoded_card1
        card2 = CardsFromFirebase().decoded_card1

        puanTextView.text = " Puan: ${puan.toString()}"

        val images = mutableListOf(card1,card2,card1,card2,card1,card2,card1,card2)

        // her img'i 2 kez ekleyerek çiftler oluşturuyoruz.
        images.addAll(images)

        // sırayı randomize ediyor.
        images.shuffle()

        views = listOf(imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,
            imageView10,imageView11,imageView12,imageView13,imageView14,imageView15,imageView16)

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

    private fun updateViews() {
        cards.forEachIndexed { index, card ->

            val view = views[index]
            if (card.isMatched) {
                view.alpha = 0.3f
            }
            view.setImageBitmap(if (card.isFaceUp) card.identifier else CardsBacground)
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

            matchedController(8)
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