package com.project.testgame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.gamefield.GameFieldDelegate
import com.project.gamefield.GameFieldModel
import com.project.gamefield.GameFieldView
import com.project.gamefield.GameObject

class MainActivity : AppCompatActivity(), GameFieldDelegate {

    private lateinit var gameFieldView: GameFieldView
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
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.project.gamefield.R.layout.gamefield_screen)
        gameFieldView = findViewById(com.project.gamefield.R.id.gamefield_view)
        gameFieldView.rightMargin = 10f
        gameFieldView.leftMargin = 10f
        gameFieldView.topMargin = 100f
        gameFieldView.downMargin = 100f
        GameFieldModel.columnCount = 8
        GameFieldModel.rowCount = 8
        gameFieldView.model = this
    }

    override fun onTouchActionDown(column: Int, row: Int) {
        println("start Add object at $column, $row")
    }

    override fun onTouchActionMove(column: Int, row: Int) {
        println("Adding object at $column, $row")
    }

    override fun onTouchActionUp(column: Int, row: Int) {
        println("end adding object at $column, $row")
        GameFieldModel.addGameObject(GameObject(column, row, R.drawable.chess_whiterook))
        gameFieldView.invalidate()
    }
}