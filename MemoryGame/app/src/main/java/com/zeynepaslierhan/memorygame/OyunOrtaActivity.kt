package com.zeynepaslierhan.memorygame

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_oyunorta.*

class OyunOrtaActivity : AppCompatActivity() {

    private lateinit var views: List<ImageView>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyunorta)

        val images = mutableListOf(R.drawable.albus4x4,R.drawable.bellatrix4x4,R.drawable.cho4x4,
            R.drawable.helga4x4,R.drawable.horace4x4,R.drawable.luna4x4,
            R.drawable.madam4x4, R.drawable.nymph4x4)

        // her img'i 2 kez ekleyerek çiftler oluşturuyoruz.
        images.addAll(images)

        // sırayı randomize ediyor.
        images.shuffle()

        views = listOf(imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,
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
            view.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.kart4x4)
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