package com.example.shoppinglist.Domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addItem(shopItem: ShopItem)
    suspend fun changeItem(shopItem: ShopItem)
    suspend fun deleteItem(shopItem: ShopItem)
    suspend fun getItemId(shopItemId : Int) : ShopItem
    fun getShopList() : LiveData<List<ShopItem>>

}