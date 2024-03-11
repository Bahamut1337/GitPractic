package com.example.shoppinglist.Domain

class GetItemIdUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun getItemId(shopItemId : Int) : ShopItem{
        return shopListRepository.getItemId(shopItemId)
    }
}