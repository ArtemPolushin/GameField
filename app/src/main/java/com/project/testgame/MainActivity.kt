package com.project.testgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView

//import com.project.gamefield.GameField

class MainActivity : AppCompatActivity() {

    private val figures: IntArray = intArrayOf(
        R.drawable.chess_whiterook,
        R.drawable.chess_whiteknight,
        R.drawable.chess_whitebishop,
        R.drawable.chess_whitequeen,
        R.drawable.chess_whiteking,
        R.drawable.chess_blackrook,
        R.drawable.chess_blackknight,
        R.drawable.chess_blackbishop,
        R.drawable.chess_blackqueen,
        R.drawable.chess_blackking
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val widthOfBlocks = width / 8
        val heightOfBlocks = height / 8
        val gridLayout: GridLayout = findViewById(R.id.field)
        gridLayout.rowCount = 8
        gridLayout.columnCount = 8
        gridLayout.layoutParams.width = width
        gridLayout.layoutParams.height = width
        for (i in 0..<8) {
            val imageView = ImageView(this)
            imageView.id = i
            imageView.layoutParams = ViewGroup.LayoutParams(widthOfBlocks, heightOfBlocks)
            imageView.maxWidth = widthOfBlocks
            imageView.maxHeight = heightOfBlocks
            val ind = (figures.indices).random()
            imageView.setImageResource(figures[ind])
            gridLayout.addView(imageView)
        }
        //var gameField: GameField = GameField(8,8)
    }
}