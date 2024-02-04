package com.example.composition.Domain.UseCases

import com.example.composition.Domain.Entity.Question
import com.example.composition.Domain.Repository.GameRepository

class GenerateQuestionUseCase (private val gameRepository: GameRepository)  {

    operator fun invoke(maxSumValue : Int) : Question{
        return gameRepository.generateQuestion(maxSumValue, COUNT_OPTIONS)
    }

    private companion object{
        private const val COUNT_OPTIONS = 6

    }
}