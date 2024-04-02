package com.project.gamefield.controller

interface GameFieldControllerInterface {
    /**
     * Функция для обработки начала нажатия на прямоугольник с заданными координатами
     * @param column Номер столбца прямоугольника
     * @param row Номер строки прямоугольна
     */
    fun onTouchActionDown(column: Int, row: Int)

    /**
     * Функция для обработки изменений во время процесса нажатия на прямоугольник с заданными координатами
     * @param column Номер столбца прямоугольника
     * @param row Номер строки прямоугольна
     */
    fun onTouchActionMove(column: Int, row: Int)

    /**
     * Функция для обработки конца нажатия на прямоугольник с заданными координатами
     * @param column Номер столбца прямоугольника
     * @param row Номер строки прямоугольна
     */
    fun onTouchActionUp(column: Int, row: Int)
}