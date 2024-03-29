package com.project.gamefield

object GameFieldModel {
    var columnCount: Int = 1
    var rowCount: Int = 1
    private var gameObjects = mutableSetOf<GameObject>()
    fun addGameObject(gameObject: GameObject) {
        gameObjects.add(gameObject)
    }
    fun clear() {
        gameObjects.clear()
    }

    fun getGameObjectAt(column: Int, row: Int) : GameObject? {
        for (gameObject in gameObjects) {
            if (gameObject.column == column && gameObject.row == row) {
                return gameObject
            }
        }
        return null
    }
    fun moveGameObject(gameObject: GameObject, newColumn: Int, newRow: Int) {
        val targetGameObject = getGameObjectAt(newColumn, newRow)
        if (targetGameObject != null) {
            gameObjects.remove(targetGameObject)
        }
        gameObjects.remove(gameObject)
        gameObjects.add(GameObject(newColumn, newRow, gameObject.resourceId))
    }
}