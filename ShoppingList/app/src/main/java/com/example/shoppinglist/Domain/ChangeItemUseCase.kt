package com.example.shoppinglist.Domain

class ChangeItemUseCase(private val shopListRepository: ShopListRepository) {

    fun changeItem(shopItem: ShopItem) {
        shopListRepository.changeItem(shopItem)
    }
}