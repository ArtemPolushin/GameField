package com.project.gamefield.controller

interface GameFieldControllerInterface {
    fun onTouchActionDown(column: Int, row: Int)
    fun onTouchActionMove(column: Int, row: Int)
    fun onTouchActionUp(column: Int, row: Int)
}