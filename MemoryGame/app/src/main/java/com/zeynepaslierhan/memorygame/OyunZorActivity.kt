package com.zeynepaslierhan.memorygame

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_oyunorta.*
import kotlinx.android.synthetic.main.activity_oyunzor.*
import kotlinx.android.synthetic.main.activity_oyunzor.imageView1
import kotlinx.android.synthetic.main.activity_oyunzor.imageView10
import kotlinx.android.synthetic.main.activity_oyunzor.imageView11
import kotlinx.android.synthetic.main.activity_oyunzor.imageView12
import kotlinx.android.synthetic.main.activity_oyunzor.imageView13
import kotlinx.android.synthetic.main.activity_oyunzor.imageView14
import kotlinx.android.synthetic.main.activity_oyunzor.imageView15
import kotlinx.android.synthetic.main.activity_oyunzor.imageView16
import kotlinx.android.synthetic.main.activity_oyunzor.imageView2
import kotlinx.android.synthetic.main.activity_oyunzor.imageView3
import kotlinx.android.synthetic.main.activity_oyunzor.imageView4
import kotlinx.android.synthetic.main.activity_oyunzor.imageView5
import kotlinx.android.synthetic.main.activity_oyunzor.imageView6
import kotlinx.android.synthetic.main.activity_oyunzor.imageView7
import kotlinx.android.synthetic.main.activity_oyunzor.imageView8
import kotlinx.android.synthetic.main.activity_oyunzor.imageView9

class OyunZorActivity : AppCompatActivity() {
    private lateinit var views: List<ImageView>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyunzor)

        val images = mutableListOf(R.drawable.gryffindor1,R.drawable.gryffindor2,R.drawable.gryffindor3,
            R.drawable.gryffindor4,R.drawable.slytherin1,R.drawable.slytherin2, R.drawable.slytherin3,
            R.drawable.slytherin4, R.drawable.hufflepuff1,R.drawable.hufflepuff2,R.drawable.hufflepuff3,
            R.drawable.hufflepuff4,R.drawable.ravenclaw1,R.drawable.ravenclaw2, R.drawable.ravenclaw3,
            R.drawable.ravenclaw4, R.drawable.hufflepuff4,R.drawable.hufflepuff4)

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

        object : CountDownTimer(60000,1000) {
            override fun onTick(p0: Long) {
                sayac_zor.text = "Kalan Süre: ${p0 / 1000}"
            }

            override fun onFinish() {
                sayac_zor.text = "Süre Bitti!"
                imageView1.isVisible=false; imageView2.isVisible=false; imageView3.isVisible=false; imageView4.isVisible=false;
                imageView5.isVisible=false; imageView6.isVisible=false; imageView7.isVisible=false; imageView8.isVisible=false;
                imageView9.isVisible=false; imageView10.isVisible=false; imageView11.isVisible=false; imageView12.isVisible=false;
                imageView13.isVisible=false; imageView14.isVisible=false; imageView15.isVisible=false; imageView16.isVisible=false;
                imageView17.isVisible=false; imageView18.isVisible=false; imageView19.isVisible=false; imageView20.isVisible=false;
                imageView21.isVisible=false; imageView22.isVisible=false; imageView23.isVisible=false; imageView24.isVisible=false;
                imageView25.isVisible=false; imageView26.isVisible=false; imageView27.isVisible=false; imageView28.isVisible=false;
                imageView29.isVisible=false; imageView30.isVisible=false; imageView31.isVisible=false; imageView32.isVisible=false;
                imageView33.isVisible=false; imageView34.isVisible=false; imageView35.isVisible=false; imageView36.isVisible=false;

                val handler = Handler()
                handler.postDelayed({ // Do something after 5s = 5000ms
                    setContentView(R.layout.activity_zorluk_sec)
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
            Toast.makeText(this, "Eşleşme Bulundu!", Toast.LENGTH_SHORT).show();
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }
    }
}