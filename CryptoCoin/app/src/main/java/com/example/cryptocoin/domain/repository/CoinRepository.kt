package com.example.cryptocoin.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptocoin.domain.model.CoinPriceInfo

interface CoinRepository {

    fun getPriceList() : LiveData<List<CoinPriceInfo>>
    fun getCoinInfo(fromSymbol : String) : LiveData<CoinPriceInfo>

    suspend fun loadData()
}