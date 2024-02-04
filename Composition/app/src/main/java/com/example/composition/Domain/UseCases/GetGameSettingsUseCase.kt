package com.example.composition.Domain.UseCases

import com.example.composition.Domain.Entity.GameSettings
import com.example.composition.Domain.Entity.Level
import com.example.composition.Domain.Repository.GameRepository

class GetGameSettingsUseCase(private val gameRepository: GameRepository) {

    operator fun invoke(level: Level) : GameSettings{
        return gameRepository.getSettings(level)
    }
}