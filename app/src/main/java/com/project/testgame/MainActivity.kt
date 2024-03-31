package com.project.testgame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.gamefield.controller.GameFieldControllerInterface
import com.project.gamefield.dto.GameObject
import com.project.gamefield.model.GameFieldModel
import com.project.gamefield.view.GameFieldView
import kotlin.random.Random

class MainActivity : AppCompatActivity(), GameFieldControllerInterface {

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
    override fun onTouchActionDown(column: Int, row: Int) {
    }

    override fun onTouchActionMove(column: Int, row: Int) {

    }

    override fun onTouchActionUp(column: Int, row: Int) {
        val obj = GameFieldModel.getGameObjectAt(column, row)
        if (obj == null) {
            GameFieldModel.addGameObject(GameObject(column, row, figures[Random.nextInt(figures.size)]))
        }
        else {
            GameFieldModel.removeGameObjectAt(column, row)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.project.gamefield.R.layout.gamefield_screen)
        val gameFieldView: GameFieldView = findViewById(com.project.gamefield.R.id.gamefield_view)
        gameFieldView.controller = this
        gameFieldView.rightMargin = 100f
        gameFieldView.leftMargin = 100f
        gameFieldView.topMargin = 100f
        gameFieldView.downMargin = 500f
        GameFieldModel.columnCount = 4
        GameFieldModel.rowCount = 4
    }
}