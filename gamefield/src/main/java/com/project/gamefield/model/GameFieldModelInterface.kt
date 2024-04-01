package com.project.gamefield.model

import com.project.gamefield.dto.GameObject

interface GameFieldModelInterface {
    /**
     * Добавить игровой объект на поле
     * @param gameObject Игровой объект, который будет добавлен
     */
    fun addGameObject(gameObject: GameObject)

    /**
     * Получить множество всех игровых объектов на поле
     * @return Множество всех игровых объектов на поле
     */
    fun getAllGameObjects(): Set<GameObject>

    /**
     * Удалить все игровые объекты с поля
     */
    fun removeAllGameObjects()

    /**
     * Получить игровой объект по номеру столбца и строки
     * @param column Номер столбца
     * @param row Номер строки
     * @return Игровой объект с заданными координатами или null, если такого объекта нет
     */
    fun getGameObjectAt(column: Int, row: Int) : GameObject?

    /**
     * Удалить игровой объект по номеру столбца и строки
     * @param column Номер столбца
     * @param row Номер строки
     */
    fun removeGameObjectAt(column: Int, row: Int)

    /**
     * Переместить игровой объект на новую позицию
     * @param gameObject Объект, который будет перемещён
     * @param newColumn Номер нового столбца
     * @param newRow Номер новой строки
     */
    fun moveGameObject(gameObject: GameObject, newColumn: Int, newRow: Int)

}