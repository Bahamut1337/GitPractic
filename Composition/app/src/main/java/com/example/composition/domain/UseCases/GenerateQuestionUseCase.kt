package com.example.composition.domain.UseCases

import com.example.composition.domain.entity.Question
import com.example.composition.domain.Repository.GameRepository

class GenerateQuestionUseCase (private val gameRepository: GameRepository)  {

    operator fun invoke(maxSumValue : Int) : Question{
        return gameRepository.generateQuestion(maxSumValue, COUNT_OPTIONS)
    }

    private companion object{
        private const val COUNT_OPTIONS = 6

    }
}