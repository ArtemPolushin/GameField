package com.project.gamefield.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
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

    /**
     * Маштаб поля
     */
    var scale: Float = 1f
        set(value) {
            if (value > 0f && value <= 1f) {
                throw IllegalArgumentException("The scale must be between 0 and 1")
            }
            field = value
        }

    /**
     * Количество столбцов поля
     */
    var columnCount: Int = 1
        set(value) {
            if (value < 1) {
                throw IllegalArgumentException("The column count must be positive")
            }
            field = value
        }

    /**
     * Количество строк поля
     */
    var rowCount: Int = 1
        set(value) {
            if (value < 1) {
                throw IllegalArgumentException("The row count must be positive")
            }
            field = value
        }

    /**
     * Ширина клетки поля
     */
    private var cellWidth: Float = 0f

    /**
     * Высота клетки поля
     */
    private var cellHeight: Float = 0f

    /**
     * Параметры поля
     */
    private lateinit var params: ViewGroup.MarginLayoutParams

    /**
     * Контроллер для взаимодействия с полем
     */
    var controller: GameFieldControllerInterface? = null

    /**
     * Таблица красок поля
     */
    private var paints: MutableMap<Rectangle, Paint> = mutableMapOf()

    /**
     * Установить выбранную краску для данного прямоугольника
     * @param paint Краска для установки
     * @param rectangle Прямоугольник, на который устанавливается краска
     */
    fun setPaintRectangle(paint: Paint, rectangle: Rectangle) {
        if (rectangle.column < 0 || rectangle.column >= columnCount ||
            rectangle.row < 0 || rectangle.row >= rowCount) {
            return
        }
        paints[rectangle] = paint
    }

    /**
     * Установить выбранную краску для всех прямоугольников строки
     * @param paint Краска для установки
     * @param row Индекс строки
     */
    fun setPaintRow(paint: Paint, row: Int) {
        if (row < 1 || row >= rowCount) {
            throw IllegalArgumentException("The row number must be from 0 until rowCount")
        }
        for (column in 0 until columnCount) {
            setPaintRectangle(paint, Rectangle(column, row))
        }
    }

    /**
     * Установить выбранную краску для всех прямоугольников столбца
     * @param paint Краска для установки
     * @param column Индекс столбца
     */
    fun setPaintColumn(paint: Paint, column: Int) {
        if (column < 1 || column >= columnCount) {
            throw IllegalArgumentException("The column number must be from 0 until columnCount")
        }
        for (row in 0 until columnCount) {
            setPaintRectangle(paint, Rectangle(column, row))
        }
    }
    override fun onDraw(canvas: Canvas) {
        params = layoutParams as ViewGroup.MarginLayoutParams
        cellWidth = width.toFloat() / columnCount * scale
        cellHeight = height.toFloat()/ rowCount * scale
        drawField(canvas)
        drawGameObjects(canvas)
    }

    /**
     * Нарисовать поле
     * @param canvas Холст, на котором будет нарисовано поле
     */
    private fun drawField(canvas: Canvas) {
        for (row in 0 until rowCount) {
            for (column in 0 until columnCount) {
                drawRectangle(canvas, column, row, paints[Rectangle(column, row)]?:Paint())
            }
        }
    }

    /**
     * Нарисовать прямоугольник
     * @param canvas Холст, на котором будет нарисован прямоугольник
     * @param column Индекс столбца прямоугольника
     * @param row Индекс строки прямоугольника
     * @param rectPaint Краска, которой будет нарисован прямоугольник
     */
    private fun drawRectangle(canvas: Canvas, column: Int, row: Int, rectPaint: Paint) {
        canvas.drawRect(getRectF(column, row), rectPaint)
    }

    /**
     * Нарисовать все игровые объекты
     * @param canvas Холст, на котором будет нарисованы игровые объекты
     */
    private fun drawGameObjects(canvas: Canvas) {
        val objects = GameFieldModel.getAllGameObjects()
        for (obj in objects) {
            drawGameObjectAt(canvas, obj)
        }
    }

    /**
     * Нарисовать игровой объект
     * @param canvas Холст, на котором будет нарисованы игровые объекты
     * @param gameObject Игровой объект, который будет нарисован
     */
    private fun drawGameObjectAt(canvas: Canvas, gameObject: GameObject) {
        val bitmap = BitmapFactory.decodeResource(resources, gameObject.resourceId)
        canvas.drawBitmap(bitmap, null, getRectF(gameObject.column, gameObject.row),
            paints[Rectangle(gameObject.column, gameObject.row)])
    }

    /**
     * Получить прямоугольник с координатами по номеру столбца и строки
     * @param column Номер столбца
     * @param row Номер строки
     */
    private fun getRectF(column: Int, row: Int): RectF {
        return RectF(params.leftMargin + column * cellWidth, params.topMargin + row * cellHeight,
            params.leftMargin + (column + 1) * cellWidth, params.topMargin + (row + 1) * cellHeight)
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        val column: Int = ((event.x - params.leftMargin) / cellWidth).toInt()
        val row: Int = ((event.y - params.topMargin) / cellHeight).toInt()
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