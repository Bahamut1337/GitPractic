package com.example.cryptocoin.domain.useCases

import com.example.cryptocoin.domain.repository.CoinRepository

class GetCoinInfoUseCase (private val repository: CoinRepository) {

     operator fun invoke(fromSymbol : String) = repository.getCoinInfo(fromSymbol)

}