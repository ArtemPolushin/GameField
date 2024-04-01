package com.project.gamefield.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.project.gamefield.controller.GameFieldControllerInterface
import com.project.gamefield.dto.GameObject
import com.project.gamefield.dto.Rectangle
import com.project.gamefield.model.GameFieldModel


class GameFieldView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var scale: Float = 1f
        set(value) {
            if (value > 0f && value <= 1f) {
                throw IllegalArgumentException("The scale must be between 0 and 1")
            }
            field = value
        }
    var columnCount: Int = 1
        set(value) {
            if (value < 1) {
                throw IllegalArgumentException("The column count must be positive")
            }
            field = value
        }
    var rowCount: Int = 1
        set(value) {
            if (value < 1) {
                throw IllegalArgumentException("The row count must be positive")
            }
            field = value
        }

    private var cellWidth: Float = 0f

    private var cellHeight: Float = 0f

    private lateinit var param: ViewGroup.MarginLayoutParams

    var controller: GameFieldControllerInterface? = null

    private var paints: MutableMap<Rectangle, Paint> = mutableMapOf()

    fun setPaintRectangle(paint: Paint, rectangle: Rectangle) {
        paints[rectangle] = paint
    }
    fun setPaintRow(paint: Paint, row: Int) {
        if (row < 1 || row >= rowCount) {
            throw IllegalArgumentException("The row number must be from 0 until rowCount")
        }
        for (column in 0 until columnCount) {
            setPaintRectangle(paint, Rectangle(column, row))
        }
    }

    fun setPaintColumn(paint: Paint, column: Int) {
        if (column < 1 || column >= columnCount) {
            throw IllegalArgumentException("The column number must be from 0 until columnCount")
        }
        for (row in 0 until columnCount) {
            setPaintRectangle(paint, Rectangle(column, row))
        }
    }
    override fun onDraw(canvas: Canvas) {
        param = layoutParams as ViewGroup.MarginLayoutParams
        cellWidth = width.toFloat() / columnCount * scale
        cellHeight = height.toFloat()/ rowCount * scale
        drawField(canvas)
        drawGameObjects(canvas)
    }

    private fun drawField(canvas: Canvas) {
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                drawRectangle(canvas, column, row, paints[Rectangle(column, row)]?:Paint())
            }
        }
    }

    private fun drawRectangle(canvas: Canvas, column: Int, row: Int, rectPaint: Paint) {
        canvas.drawRect(getRectF(column, row), rectPaint)
    }

    private fun drawGameObjects(canvas: Canvas) {
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                GameFieldModel.getGameObjectAt(column, row)?.let {
                    drawGameObjectAt(canvas, it)
                }
            }
        }
    }

    private fun drawGameObjectAt(canvas: Canvas, gameObject: GameObject) {
        val bitmap = BitmapFactory.decodeResource(resources, gameObject.resourceId)
        canvas.drawBitmap(bitmap, null, getRectF(gameObject.column, gameObject.row),
            paints[Rectangle(gameObject.column, gameObject.row)])
    }

    private fun getRectF(column: Int, row: Int): RectF {
        return RectF(param.leftMargin + column * cellWidth, param.topMargin + row * cellHeight,
            param.leftMargin + (column + 1) * cellWidth, param.topMargin + (row + 1) * cellHeight)
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        val column: Int = ((event.x - param.leftMargin) / cellWidth).toInt()
        val row: Int = ((event.y - param.topMargin) / cellHeight).toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                controller?.onTouchActionDown(column, row)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                controller?.onTouchActionMove(column, row)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                controller?.onTouchActionUp(column, row)
                invalidate()
            }
        }
        return true
    }
}