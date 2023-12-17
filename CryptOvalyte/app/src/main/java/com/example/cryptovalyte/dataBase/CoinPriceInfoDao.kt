package com.example.cryptovalyte.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptovalyte.pojo.CoinPriceInfo

@Dao
interface CoinPriceInfoDao {

    @Query("SELECT * FROM FULL_PRICE_LIST ORDER BY lastupdate DESC")
    fun getPriceList() : LiveData<List<CoinPriceInfo>>

    @Query("SELECT * FROM FULL_PRICE_LIST WHERE fromsymbol == :fSym LIMIT 1 ")
    fun getPriceInfoAboutCoin(fSym : String) : LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun incertPriceList(priceList : List<CoinPriceInfo>)
}