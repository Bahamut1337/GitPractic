package com.example.cryptocoin.domain.useCases

import com.example.cryptocoin.domain.repository.CoinRepository

class LoadDataUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke() = repository.loadData()
}