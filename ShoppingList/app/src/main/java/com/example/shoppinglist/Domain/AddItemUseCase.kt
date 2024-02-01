package com.example.shoppinglist.Domain

class AddItemUseCase(private val shopListRepository: ShopListRepository) {

    fun addItem(shopItem: ShopItem){
        shopListRepository.addItem(shopItem)
    }
}