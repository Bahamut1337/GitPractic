package com.example.shoppinglist.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.Domain.ShopItem
import com.example.shoppinglist.Domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl : ShopListRepository  {

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()

    private var aoutoId = 0

    init {
        for(i in 0..10){
            val item = ShopItem("Name $i",i,true)
            addItem(item)
        }
    }

    override fun addItem(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = aoutoId++
        }
        shopList.add(shopItem)
        updateLiveDate()
    }

    override fun changeItem(shopItem: ShopItem) {
        val oldElement = getItemId(shopItem.id)
        shopList.remove(oldElement)
        addItem(shopItem)
    }

    override fun deleteItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateLiveDate()
    }

    override fun getItemId(shopItemId: Int): ShopItem {
        return shopList
            .find { it.id == shopItemId }
            ?: throw RuntimeException ("Element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    private fun updateLiveDate(){
        shopListLiveData.value = shopList.toList()
    }
}