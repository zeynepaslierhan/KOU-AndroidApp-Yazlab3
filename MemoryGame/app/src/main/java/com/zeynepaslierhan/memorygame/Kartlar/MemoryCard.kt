package com.zeynepaslierhan.memorygame.Kartlar

import android.graphics.Bitmap

data class MemoryCard (val identifier: Bitmap?, var isFaceUp: Boolean = false, var isMatched: Boolean = false)
