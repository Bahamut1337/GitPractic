package com.example.cryptovalyte

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptovalyte.api.ApiFactory
import com.example.cryptovalyte.dataBase.AppDataBaseCrypto
import com.example.cryptovalyte.pojo.CoinPriceInfo
import com.example.cryptovalyte.pojo.CoinPriceInfoRowData
import com.google.gson.Gson
import hu.akarnokd.rxjava3.bridge.RxJavaBridge

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDataBaseCrypto.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    //!!! код в этих скобках будет вызываться автоматически когда мы инициализируем наш класс в мейне!!!///
    init {
        loadData()
    }

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun getDetailInfo(fsym : String) : LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fsym)
    }


    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinInfo(limit = 25)
            .map { it.data?.map { it.CoinInfo?.name }?.joinToString(",") as String }
            .flatMap { ApiFactory.apiService.getFullPriceList(fsymbols = it) }
            .map { getPriceCoinInfo(it) }
            .delaySubscription(5,TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                //получем лист имен криптовалют и с помощью .joinToString делаем из колекции просто строку

                if (it != null) {
                    db.coinPriceInfoDao().incertPriceList(it)
                }
                Log.d("TEST_OF_LOADING_DATA", it.toString())
            }, {
                Log.d("TEST_OF_LOADING_DATA", it.message.toString())
            })
        compositeDisposable.dispose()
    }

    private fun getPriceCoinInfo(coinPriceInfoRowData: CoinPriceInfoRowData): List<CoinPriceInfo>? {
        val jsonObject = coinPriceInfoRowData.coinPriceInfoJsonObject

        val result = ArrayList<CoinPriceInfo>()
        if (jsonObject == null) return result


        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }

        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}