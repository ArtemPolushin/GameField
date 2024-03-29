package com.project.gamefield

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


class GameFieldView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var scale: Float = 1f
        set(value) {
            if (value > 0f && value <= 1f) {
                field = value
            }
        }
    var leftMargin: Float = 0f
        set(value) {
            if (value >= 0f) {
                field = value
            }
        }

    var rightMargin: Float = 0f
        set(value) {
            if (value >= 0f) {
                field = value
            }
        }
    var topMargin: Float = 0f
        set(value) {
            if (value >= 0f) {
                field = value
            }
        }
    var downMargin: Float = 0f
        set(value) {
            if (value >= 0f) {
                field = value
            }
        }

    var paint = Paint()

    private var cellWidth: Float = 0f

    private var cellHeight: Float = 0f

    var model: GameFieldDelegate? = null

    override fun onDraw(canvas: Canvas) {
        cellWidth = (width.toFloat() - leftMargin - rightMargin) / GameFieldModel.columnCount * scale
        cellHeight = (height.toFloat() - topMargin - downMargin) / GameFieldModel.rowCount * scale
        drawField(canvas)
        drawGameObjects(canvas)
    }

    private fun drawField(canvas: Canvas) {
        for (row in 0 until GameFieldModel.rowCount) {
            for (column in 0 until GameFieldModel.columnCount) {
                paint.color = if ((row + column) % 2 == 0) Color.DKGRAY else Color.LTGRAY
                drawRectangle(canvas, column, row, paint)
            }
        }
    }

    private fun drawRectangle(canvas: Canvas, column: Int, row: Int, rectPaint: Paint) {
        canvas.drawRect(getRectF(column, row), rectPaint)
    }

    private fun drawGameObjects(canvas: Canvas) {
        for (row in 0 until GameFieldModel.rowCount) {
            for (column in 0 until GameFieldModel.columnCount) {
                GameFieldModel.getGameObjectAt(column, row)?.let {
                    drawGameObjectAt(canvas, it)
                }
            }
        }
    }

    private fun drawGameObjectAt(canvas: Canvas, gameObject: GameObject) {
        val bitmap = BitmapFactory.decodeResource(resources, gameObject.resourceId)
        canvas.drawBitmap(bitmap, null, getRectF(gameObject.column, gameObject.row), paint)
    }

    private fun getRectF(column: Int, row: Int): RectF {
        return RectF(leftMargin + column * cellWidth, topMargin + row * cellHeight,
            leftMargin + (column + 1) * cellWidth, topMargin + (row + 1) * cellHeight)
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        val column: Int = ((event.x - leftMargin) / cellWidth).toInt()
        val row: Int = ((event.y - topMargin) / cellHeight).toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                model?.onTouchActionDown(column, row)
            }
            MotionEvent.ACTION_MOVE -> {
                model?.onTouchActionMove(column, row)
            }
            MotionEvent.ACTION_UP -> {
                model?.onTouchActionUp(column, row)
            }
        }
        return true
    }
}