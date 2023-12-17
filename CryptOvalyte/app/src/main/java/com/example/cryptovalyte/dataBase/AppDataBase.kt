package com.example.cryptovalyte.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptovalyte.pojo.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class AppDataBaseCrypto : RoomDatabase() {

    companion object{
        private var db : AppDataBaseCrypto? = null
        private const val cryptoDB = "cryptoDB"
        private val lock = Any()

        fun getInstance(contex : Context) : AppDataBaseCrypto{
            synchronized(lock){
            db?.let { return it }//этот код выполнится только в том случае если база данных не равна null
                val const_db = Room.databaseBuilder(contex, AppDataBaseCrypto::class.java, cryptoDB).build()
                db = const_db
                return const_db
            }
        }
    }

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}