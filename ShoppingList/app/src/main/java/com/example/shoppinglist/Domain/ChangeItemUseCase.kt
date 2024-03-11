package com.example.shoppinglist.Domain

class ChangeItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun changeItem(shopItem: ShopItem) {
        shopListRepository.changeItem(shopItem)
    }
}