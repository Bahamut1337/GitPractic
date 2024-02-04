package com.example.composition.Domain.Entity

data class GameSettings(
    val maxSumValue : Int,
    val minCountOfRightAnswer : Int,
    val minParcentOfRightAnswer : Int,
    val gameTime : Int) {
}