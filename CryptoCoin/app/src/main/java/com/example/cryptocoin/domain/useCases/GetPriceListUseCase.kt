package com.example.cryptocoin.domain.useCases

import com.example.cryptocoin.domain.repository.CoinRepository

class GetPriceListUseCase(private val repository: CoinRepository) {

    operator fun invoke() = repository.getPriceList()
}