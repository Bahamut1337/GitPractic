package com.example.shoppinglist.Domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addItem(shopItem: ShopItem)
    fun changeItem(shopItem: ShopItem)
    fun deleteItem(shopItem: ShopItem)
    fun getItemId(shopItemId : Int) : ShopItem
    fun getShopList() : LiveData<List<ShopItem>>

}