package com.zeynepaslierhan.memorygame

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_oyun_basit.*

private const val TAG = "Basit Oyun Activity"

class MainActivity2 : AppCompatActivity() {

    private lateinit var views: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyun_basit)

        val images = mutableListOf(R.drawable.harrypotter2x2,R.drawable.voldemort2x2)

        // her img'i 2 kez ekleyerek çiftler oluşturuyoruz.
        images.addAll(images)

        // sırayı randomize ediyor.
        images.shuffle()

        views = listOf(kart_2_1, kart_2_2, kart_2_3, kart_2_4)

        views.forEachIndexed { index, view ->
            view.setOnClickListener {
                Log.i(TAG, "View Clicked!")
                view.setImageResource(images[index])
            }
        }
    }
}
