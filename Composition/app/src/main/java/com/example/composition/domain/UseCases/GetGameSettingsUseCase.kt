package com.example.composition.domain.UseCases

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.Repository.GameRepository

class GetGameSettingsUseCase(private val gameRepository: GameRepository) {

    operator fun invoke(level: Level) : GameSettings{
        return gameRepository.getSettings(level)
    }
}