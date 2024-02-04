package com.example.composition.Domain.Repository

import com.example.composition.Domain.Entity.GameSettings
import com.example.composition.Domain.Entity.Level
import com.example.composition.Domain.Entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue : Int,
        countOfOptions : Int
    ) : Question

    fun getSettings(level: Level) : GameSettings
}