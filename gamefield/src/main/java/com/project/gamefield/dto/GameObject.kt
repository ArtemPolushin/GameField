package com.project.gamefield.dto

/**
 * Игровой объект, расположенный на поле
 * @param column Номер столбца прямоугольника, на котором расположен игровой объект
 * @param row Номер строки прямоугольника, на котором расположен игровой объект
 * @param resourceId Индекс ресурса игрового объекта
 */
data class GameObject(val column: Int, val row: Int, val resourceId: Int)
