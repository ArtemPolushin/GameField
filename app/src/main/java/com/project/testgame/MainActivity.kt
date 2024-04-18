package com.project.testgame

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import kotlin.random.Random
import androidx.appcompat.app.AppCompatActivity

import com.project.gamefield.controller.GameFieldControllerInterface
import com.project.gamefield.dto.GameObject
import com.project.gamefield.dto.Cell
import com.project.gamefield.model.GameFieldModel
import com.project.gamefield.view.GameFieldView


class MainActivity : AppCompatActivity(), GameFieldControllerInterface {

    private var movingObj: GameObject? = null
    private lateinit var gameFieldView: GameFieldView
    override fun onTouchActionDown(column: Int, row: Int) {
        movingObj = GameFieldModel.getGameObjectAt(column, row)
    }
    override fun onTouchActionMove(column: Int, row: Int) {
    }
    override fun onTouchActionUp(column: Int, row: Int) {
        movingObj ?: return
        val obj = GameFieldModel.getGameObjectAt(column, row)
        if (fruits.contains(movingObj!!.resourceId)) {
            if (obj == null) {
                GameFieldModel.removeGameObjectAt(movingObj!!.column, movingObj!!.row)
                GameFieldModel.addGameObject(GameObject(column, row, movingObj!!.resourceId))
            } else {
                GameFieldModel.removeGameObjectAt(column, row)
                GameFieldModel.removeGameObjectAt(movingObj!!.column, movingObj!!.row)
                GameFieldModel.addGameObject(GameObject(column, row, movingObj!!.resourceId))
                GameFieldModel.addGameObject(GameObject(movingObj!!.column, movingObj!!.row, obj.resourceId))
            }
            return
        }
        if (obj != null) {
            GameFieldModel.removeGameObjectAt(column, row)
        }
        GameFieldModel.removeGameObjectAt(movingObj!!.column, movingObj!!.row)
        GameFieldModel.addGameObject(GameObject(column, row, movingObj!!.resourceId))
        movingObj = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.project.gamefield.R.layout.gamefield_screen)
        gameFieldView = findViewById(com.project.gamefield.R.id.gamefield_view)
        val param = gameFieldView.layoutParams
        param.height = 1250
        param.width = 1000
        gameFieldView.layoutParams = param
        gameFieldView.columnCount = 8
        gameFieldView.rowCount = 10

        for (i in 0..<gameFieldView.columnCount / 2) {
            for (j in 0..<gameFieldView.rowCount) {
                val paint = Paint()
                paint.color = if ((i+j) % 2 == 0) Color.YELLOW else Color.GREEN
                paint.alpha = 300
                gameFieldView.setPaintCell(paint, Cell(i,j))
            }
        }

        for (i in gameFieldView.columnCount/2..<gameFieldView.columnCount ) {
            for (j in 0..<gameFieldView.rowCount ) {
                val paint = Paint()
                paint.color = Color.TRANSPARENT
                paint.alpha = 180
                gameFieldView.setPaintCell(paint, Cell(i,j))
            }
        }


        gameFieldView.controller = this
        gameFieldView.setBackgroundResource(R.drawable.city)

        fillChess()
        fillFruits()
    }


    private fun fillFruits() {
        for (i in gameFieldView.columnCount/2 ..<gameFieldView.columnCount) {
            for (j in 0..<gameFieldView.rowCount) {
                GameFieldModel.addGameObject(GameObject(i, j, fruits[Random.nextInt(fruits.size)]))
            }
        }
    }

    private fun fillChess() {
        GameFieldModel.addGameObject(GameObject(0,0,R.drawable.chess_blackrook))
        GameFieldModel.addGameObject(GameObject(1,0,R.drawable.chess_blackknight))
        GameFieldModel.addGameObject(GameObject(2,0,R.drawable.chess_blackbishop))
        GameFieldModel.addGameObject(GameObject(3,0,R.drawable.chess_blackqueen))
        GameFieldModel.addGameObject(GameObject(0,gameFieldView.rowCount-1,
            R.drawable.chess_whiterook))
        GameFieldModel.addGameObject(GameObject(1,gameFieldView.rowCount-1,
            R.drawable.chess_whiteknight))
        GameFieldModel.addGameObject(GameObject(2,gameFieldView.rowCount-1,
            R.drawable.chess_whitebishop))
        GameFieldModel.addGameObject(GameObject(3,gameFieldView.rowCount-1,
            R.drawable.chess_whitequeen))
        for (i in 0..3) {
            GameFieldModel.addGameObject(GameObject(i,1,R.drawable.chess_blackpawn))
            GameFieldModel.addGameObject(GameObject(i,gameFieldView.rowCount-2,
                R.drawable.chess_whitepawn))
        }
    }

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

    private val fruits: IntArray = intArrayOf(
        R.drawable.apple,
        R.drawable.banana,
        R.drawable.berry,
        R.drawable.mango,
        R.drawable.pineapple
    )
}