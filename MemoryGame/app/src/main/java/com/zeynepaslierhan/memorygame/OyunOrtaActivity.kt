package com.zeynepaslierhan.memorygame

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

class OyunOrtaActivity : AppCompatActivity() {

    private lateinit var views: List<ImageView>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyunorta)

        val images = mutableListOf(R.drawable.gryffindor1,R.drawable.hufflepuff1,R.drawable.gryffindor3,
            R.drawable.slytherin2,R.drawable.slytherin3,R.drawable.ravenclaw1,
            R.drawable.ravenclaw4, R.drawable.gryffindor2)

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

        object : CountDownTimer(60000,1000) {
            override fun onTick(p0: Long) {
                sayac_orta.text = "Kalan Süre: ${p0 / 1000}"
            }

            override fun onFinish() {
                sayac_orta.text = "Süre Bitti!"
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