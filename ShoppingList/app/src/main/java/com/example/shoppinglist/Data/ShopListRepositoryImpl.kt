package com.example.shoppinglist.Data

import android.app.Application
import android.content.Context
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.shoppinglist.Domain.ShopItem
import com.example.shoppinglist.Domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

class ShopListRepositoryImpl(application: Application) : ShopListRepository  {



    private val mapper = ShopListMapper()
    private val dao = AppDataBase.getInstance(application).shopListDao()

    override suspend fun addItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapEntityItemToDB(shopItem))
    }

    override suspend fun changeItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapEntityItemToDB(shopItem))
    }

    override suspend fun deleteItem(shopItem: ShopItem) {
        dao.deleteShopItem(shopItem.id)
    }

    override suspend fun getItemId(shopItemId: Int): ShopItem {
        val dbModel = dao.getShopItem(shopItemId)
        return mapper.mapDBItemToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return dao.getFullList().map { mapper.mapListDBToEntityList(it) }
    }


}