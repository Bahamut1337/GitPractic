package com.example.shoppinglist.Presentation


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.Data.ShopListRepositoryImpl
import com.example.shoppinglist.Domain.ChangeItemUseCase
import com.example.shoppinglist.Domain.DeleteItemUseCase
import com.example.shoppinglist.Domain.GetShopListUseCase
import com.example.shoppinglist.Domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopListUseCase = DeleteItemUseCase(repository)
    private val changeShopListUseCase = ChangeItemUseCase(repository)


    val shopList = getShopListUseCase.getShopList()

    fun deletShopList(shopItem: ShopItem){
        viewModelScope.launch {
            deleteShopListUseCase.deleteItem(shopItem)
        }

    }

    fun changeItemEnable(shopItem: ShopItem){
        viewModelScope.launch {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            changeShopListUseCase.changeItem(newItem)
        }


    }

}