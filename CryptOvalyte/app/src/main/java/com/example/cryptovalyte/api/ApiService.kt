package com.example.cryptovalyte.api

import com.example.cryptovalyte.pojo.CoinInfoListOfDate
import com.example.cryptovalyte.pojo.CoinPriceInfoRowData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey : String = "",
        @Query(QUERY_PARAM_LIMIT) limit : Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) symbol :String = CURRENCY)
    : Single<CoinInfoListOfDate>


    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey : String = "",
        @Query(QUERY_PARAM_TO_SYMBOLs) tsymbols :String = CURRENCY,
        @Query(QUERY_PARAM_FROM_SYMBOLs) fsymbols :String
    ) : Single<CoinPriceInfoRowData>


    companion object{
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"


        private const val QUERY_PARAM_TO_SYMBOLs = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLs = "fsyms"

        private const val CURRENCY = "USD"
    }


}