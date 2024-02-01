package com.example.shoppinglist.Presentation


import androidx.lifecycle.ViewModel
import com.example.shoppinglist.Data.ShopListRepositoryImpl
import com.example.shoppinglist.Domain.ChangeItemUseCase
import com.example.shoppinglist.Domain.DeleteItemUseCase
import com.example.shoppinglist.Domain.GetShopListUseCase
import com.example.shoppinglist.Domain.ShopItem


class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopListUseCase = DeleteItemUseCase(repository)
    private val changeShopListUseCase = ChangeItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()



    fun deletShopList(shopItem: ShopItem){
        deleteShopListUseCase.deleteItem(shopItem)

    }

    fun changeItemEnable(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        changeShopListUseCase.changeItem(newItem)

    }



}