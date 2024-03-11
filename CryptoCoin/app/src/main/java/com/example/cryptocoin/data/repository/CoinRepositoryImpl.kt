package com.example.cryptocoin.data.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptocoin.data.database.AppDatabase
import com.example.cryptocoin.data.mapper.CoinMapper
import com.example.cryptocoin.data.network.ApiFactory
import com.example.cryptocoin.data.network.ApiService
import com.example.cryptocoin.domain.model.CoinPriceInfo
import com.example.cryptocoin.domain.repository.CoinRepository
import kotlinx.coroutines.delay


class CoinRepositoryImpl(private val application: Application) : CoinRepository {

    private val coinDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val coinMapper = CoinMapper()
    private val apiService  = ApiFactory.apiService

    override fun getPriceList(): LiveData<List<CoinPriceInfo>> {
        return coinDao.getPriceList().map {
            coinMapper.mapListDBToEntityList(it)
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinPriceInfo> {
        return  coinDao.getPriceInfoAboutCoin(fromSymbol).map {
            coinMapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true){

            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fromSymbol = coinMapper.mapCoinListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbol)
                val coinInfoDtoList = coinMapper.mapJsonToDto(jsonContainer)
                val dbModelList = coinInfoDtoList.map {
                    coinMapper.mapDtoToDbModel(it)
                }
                coinDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }
}