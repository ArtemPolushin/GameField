package com.project.gamefield.model

import com.project.gamefield.dto.GameObject

interface GameFieldModelInterface {
    var columnCount: Int
    var rowCount: Int
    fun addGameObject(gameObject: GameObject)
    fun getAllGameObjects(): Set<GameObject>
    fun removeAllGameObjects()
    fun getGameObjectAt(column: Int, row: Int) : GameObject?
    fun removeGameObjectAt(column: Int, row: Int)
    fun moveGameObject(gameObject: GameObject, newColumn: Int, newRow: Int)

}