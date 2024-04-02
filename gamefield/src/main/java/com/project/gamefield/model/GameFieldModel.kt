package com.project.gamefield.model

import com.project.gamefield.dto.GameObject

object GameFieldModel : GameFieldModelInterface {

    /**
     * Коллекция из всех игровых объектов на поле
     */
    private var gameObjects = mutableSetOf<GameObject>()
    override fun addGameObject(gameObject: GameObject) {
        gameObjects.add(gameObject)
    }
    override fun removeAllGameObjects() {
       gameObjects.clear()
    }
    override fun removeGameObjectAt(column: Int, row: Int) {
        getGameObjectAt(column, row)?.let {
            gameObjects.remove(it)
        }
    }
    override fun getGameObjectAt(column: Int, row: Int) : GameObject? {
        for (gameObject in gameObjects) {
            if (gameObject.column == column && gameObject.row == row) {
                return gameObject
            }
        }
        return null
    }
    override fun moveGameObject(gameObject: GameObject, newColumn: Int, newRow: Int) {
        val targetGameObject = getGameObjectAt(newColumn, newRow)
        if (targetGameObject != null) {
            gameObjects.remove(targetGameObject)
        }
        gameObjects.remove(gameObject)
        gameObjects.add(GameObject(newColumn, newRow, gameObject.resourceId))
    }
    override fun getAllGameObjects(): Set<GameObject> {
        return gameObjects
    }
}