package com.project.gamefield

import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity

class GameField (val numberHorizontalBlocks: Int,
                 val numberVerticalBlocks: Int) : AppCompatActivity() {
    private var height: Int = 0
    private var width: Int = 0
    init {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels
    }

}