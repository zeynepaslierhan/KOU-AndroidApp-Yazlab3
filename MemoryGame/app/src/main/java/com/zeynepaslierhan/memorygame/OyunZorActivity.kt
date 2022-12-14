package com.zeynepaslierhan.memorygame

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_oyunzor.*

class OyunZorActivity : AppCompatActivity() {
    private lateinit var views: List<ImageView>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyunzor)

        val images = mutableListOf(R.drawable.arthur6x6,R.drawable.cedric6x6,R.drawable.dolores6x6,
            R.drawable.draco6x6,R.drawable.snape6x6,R.drawable.gilderoy6x6, R.drawable.hagrid6x6,
            R.drawable.hermoine6x6, R.drawable.sybil6x6,R.drawable.lucius6x6,R.drawable.lupin6x6,
            R.drawable.narcissa6x6,R.drawable.newt6x6,R.drawable.pamona6x6, R.drawable.quirrell6x6,
            R.drawable.ron6x6, R.drawable.rowena6x6,R.drawable.sirius6x6)

        // her img'i 2 kez ekleyerek çiftler oluşturuyoruz.
        images.addAll(images)

        // sırayı randomize ediyor.
        images.shuffle()

        views = listOf(kart_6_1,kart_6_2,kart_6_3,kart_6_4,kart_6_5,kart_6_6,kart_6_7,kart_6_8,kart_6_9,
            kart_6_10,kart_6_11,kart_6_12,kart_6_13,kart_6_14,kart_6_15,kart_6_16,kart_6_17,kart_6_18,
            kart_6_19,kart_6_20,kart_6_21,kart_6_22,kart_6_23,kart_6_24,kart_6_25,kart_6_26,kart_6_27,
            kart_6_28,kart_6_29,kart_6_30,kart_6_31,kart_6_32,kart_6_33,kart_6_34,kart_6_35,kart_6_36)

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
            view.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.kart6x6)
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