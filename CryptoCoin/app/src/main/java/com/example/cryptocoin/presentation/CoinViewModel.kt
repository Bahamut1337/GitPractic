package com.example.cryptocoin.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptocoin.data.repository.CoinRepositoryImpl
import com.example.cryptocoin.domain.model.CoinPriceInfo
import com.example.cryptocoin.domain.useCases.GetCoinInfoUseCase
import com.example.cryptocoin.domain.useCases.GetPriceListUseCase
import com.example.cryptocoin.domain.useCases.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)


    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val getPriceListUseCase = GetPriceListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getPriceListUseCase()

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
        return getCoinInfoUseCase(fSym)
    }

    init{
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}