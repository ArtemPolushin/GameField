package com.project.gamefield

interface GameFieldDelegate {
    fun onTouchActionDown(column: Int, row: Int) {}
    fun onTouchActionMove(column: Int, row: Int) {}
    fun onTouchActionUp(column: Int, row: Int) {}
}