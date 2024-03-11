package com.example.cryptocoin.data.mapper


import com.example.cryptocoin.data.database.CoinPriceInfoDbModel
import com.example.cryptocoin.data.network.model.CoinInfoDto
import com.example.cryptocoin.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptocoin.data.network.model.CoinNamesListDto
import com.example.cryptocoin.domain.model.CoinPriceInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CoinMapper {



    fun mapDtoToDbModel(coinInfoDto: CoinInfoDto) : CoinPriceInfoDbModel{
        return CoinPriceInfoDbModel(
            fromSymbol = coinInfoDto.fromsymbol,
            toSymbol = coinInfoDto.tosymbol,
            price = coinInfoDto.price,
            lastUpdate = coinInfoDto.lastupdate,
            highDay = coinInfoDto.highday,
            lowDay = coinInfoDto.lowday,
            lastMarket = coinInfoDto.lastmarket,
            imageUrl = BASE_IMAGE_URL + coinInfoDto.imageurl
        )
    }

    fun mapJsonToDto(jsonContainerDto: CoinInfoJsonContainerDto) : List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainerDto.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapCoinListToString(coinNamesListDto : CoinNamesListDto) : String{
       return coinNamesListDto.names?.map { it.coinNameDto?.name }?.joinToString(",") ?: ""
    }



    fun mapDbModelToEntity(coinPriceInfoDbModel : CoinPriceInfoDbModel) : CoinPriceInfo {
        return CoinPriceInfo(
            fromSymbol = coinPriceInfoDbModel.fromSymbol,
            toSymbol = coinPriceInfoDbModel.toSymbol,
            price = coinPriceInfoDbModel.price,
            lastUpdate = convertTimestampToTime(coinPriceInfoDbModel.lastUpdate),
            highDay = coinPriceInfoDbModel.highDay,
            lowDay = coinPriceInfoDbModel.lowDay,
            lastMarket = coinPriceInfoDbModel.lastMarket,
            imageUrl = coinPriceInfoDbModel.imageUrl
        )
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    fun mapListDBToEntityList(list: List<CoinPriceInfoDbModel>)  = list.map {
        mapDbModelToEntity(it)
    }

    companion object{
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }

}