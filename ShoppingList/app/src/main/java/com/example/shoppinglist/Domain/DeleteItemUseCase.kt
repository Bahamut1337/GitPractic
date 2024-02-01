package com.example.shoppinglist.Domain

class DeleteItemUseCase(private val shopListRepository: ShopListRepository) {

    fun deleteItem(shopItem: ShopItem){
        shopListRepository.deleteItem(shopItem)
    }
}