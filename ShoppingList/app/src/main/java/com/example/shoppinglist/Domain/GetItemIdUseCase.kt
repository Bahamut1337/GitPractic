package com.example.shoppinglist.Domain

class GetItemIdUseCase(private val shopListRepository: ShopListRepository) {

    fun getItemId(shopItemId : Int) : ShopItem{
        return shopListRepository.getItemId(shopItemId)
    }
}