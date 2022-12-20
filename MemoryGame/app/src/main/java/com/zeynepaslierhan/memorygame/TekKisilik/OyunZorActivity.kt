package com.zeynepaslierhan.memorygame.TekKisilik

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
import com.zeynepaslierhan.memorygame.Kartlar.MemoryCard
import com.zeynepaslierhan.memorygame.R
import kotlinx.android.synthetic.main.activity_oyunzor.*

class OyunZorActivity : AppCompatActivity() {

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

    private var KalanSüre : Long = 45

    fun timer(){

        object : CountDownTimer(45000,1000) {
            override fun onTick(p0: Long) {
                if(matchCounter == 18)
                {
                    sayac.text = "Tebrikler!!!"
                }else{
                    sayac.text = "Kalan Süre: ${p0 / 1000}"
                }
            }

            override fun onFinish() {
                if(matchCounter != 8)
                {
                    MPlost?.start()

                    sayac.text = "Süre Bitti!"

                    imageView1.isVisible=false; imageView2.isVisible=false; imageView3.isVisible=false; imageView4.isVisible=false;
                    imageView5.isVisible=false; imageView6.isVisible=false; imageView7.isVisible=false; imageView8.isVisible=false;
                    imageView9.isVisible=false; imageView10.isVisible=false; imageView11.isVisible=false; imageView12.isVisible=false;
                    imageView13.isVisible=false; imageView14.isVisible=false; imageView15.isVisible=false; imageView16.isVisible=false;
                    imageView17.isVisible=false; imageView18.isVisible=false; imageView19.isVisible=false; imageView20.isVisible=false;
                    imageView21.isVisible=false; imageView22.isVisible=false; imageView23.isVisible=false; imageView24.isVisible=false;
                    imageView25.isVisible=false; imageView26.isVisible=false; imageView27.isVisible=false; imageView28.isVisible=false;
                    imageView29.isVisible=false; imageView30.isVisible=false; imageView31.isVisible=false; imageView32.isVisible=false;
                    imageView33.isVisible=false; imageView34.isVisible=false; imageView35.isVisible=false; imageView36.isVisible=false;

                }
                val handler = Handler()
                handler.postDelayed({ // Do something after 5s = 5000ms
                    val intent = Intent(this@OyunZorActivity, zorluk_secActivity::class.java)
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
                val intent = Intent(this@OyunZorActivity, zorluk_secActivity::class.java)
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
        setContentView(R.layout.activity_oyunzor)


        // Sayfa Yapısı
        musicSetting()
        timer()

        puanTextView.text = " Puan: ${puan.toString()}"

        val images = mutableListOf(R.drawable.gryffindor1,R.drawable.gryffindor2,R.drawable.gryffindor3,R.drawable.gryffindor4,
            R.drawable.hufflepuff1,R.drawable.hufflepuff2,R.drawable.hufflepuff3,R.drawable.hufflepuff4,
            R.drawable.ravenclaw1,R.drawable.ravenclaw2,R.drawable.ravenclaw3,R.drawable.ravenclaw4,
            R.drawable.slytherin1,R.drawable.slytherin2,R.drawable.slytherin3,R.drawable.slytherin4,
            R.drawable.gryffindor3,R.drawable.slytherin3)

        // her img'i 2 kez ekleyerek çiftler oluşturuyoruz.
        images.addAll(images)

        // sırayı randomize ediyor.
        images.shuffle()

        views = listOf(imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,
            imageView7,imageView8,imageView9,imageView10,imageView11,imageView12,
            imageView13,imageView14,imageView15,imageView16,imageView17,imageView18,
            imageView19,imageView20,imageView21,imageView22,imageView23,imageView24,
            imageView25, imageView26,imageView27,imageView28,imageView29,imageView30,
            imageView31,imageView32,imageView33,imageView34,imageView35,imageView36)


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

            var süre = (KalanSüre/1000).toInt()

            if (cards[position1].identifier == R.drawable.gryffindor1)
            {
                puan += (20*2*2)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.gryffindor2)
            {
                puan += (10*2*2)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.gryffindor3)
            {
                puan += (10*2*2)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.gryffindor4)
            {
                puan += (8*2*2)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.hufflepuff1)
            {
                puan += (20*2*1)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.hufflepuff2)
            {
                puan += (18*2*1)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.hufflepuff3)
            {
                puan += (13*2*1)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.hufflepuff4)
            {
                puan += (18*2*1)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.ravenclaw1)
            {
                puan += (20*2*1)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.ravenclaw2)
            {
                puan += (13*2*1)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.ravenclaw3)
            {
                puan += (9*2*1)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.ravenclaw4)
            {
                puan += (15*2*1)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.slytherin1)
            {
                puan += (20*2*2)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.slytherin2)
            {
                puan += (18*2*2)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.slytherin3)
            {
                puan += (13*2*2)*(süre/10)
            }

            else if (cards[position1].identifier == R.drawable.slytherin4)
            {
                puan += (16*2*2)*(süre/10)
            }

            puanTextView.text = " Puan: ${puan.toString()}"

            Toast.makeText(this, "Eşleşme Bulundu!", Toast.LENGTH_SHORT).show();
            cards[position1].isMatched = true
            cards[position2].isMatched = true

            matchedController(16)

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        MPbacground?.stop()
        MPmatch?.stop()
        MPwin?.stop()
        MPlost?.stop()
    }
}