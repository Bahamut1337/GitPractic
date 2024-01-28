package com.example.shoppinglist.Presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.Data.ShopListRepositoryImpl
import com.example.shoppinglist.Domain.AddItemUseCase
import com.example.shoppinglist.Domain.ChangeItemUseCase
import com.example.shoppinglist.Domain.GetItemIdUseCase
import com.example.shoppinglist.Domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName :LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount :LiveData<Boolean>
        get() = _errorInputCount

    private val repository = ShopListRepositoryImpl

    private val addItemUseCase = AddItemUseCase(repository)
    private val getItemIdUseCase = GetItemIdUseCase(repository)
    private val changeItemUseCase = ChangeItemUseCase(repository)


    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem : LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen : LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(id : Int) {
        val item = getItemIdUseCase.getItemId(id)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValue = validateInput(name,count)
        if(fieldValue){
            val item = ShopItem(name,count,true)
            addItemUseCase.addItem(item)
            _shouldCloseScreen.value =Unit
        }

    }

    fun changeShopItem(inputName: String?, inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValue = validateInput(name,count)
        if(fieldValue){
            _shopItem.value?.let {
                val item = it.copy(name = name,count = count)
                changeItemUseCase.changeItem(item)
                _shouldCloseScreen.value =Unit
            }

        }

    }

    fun parseName(inputName: String?) : String{
        return inputName?.trim() ?: ""
    }

    fun parseCount(inputCount : String?) : Int{
        return try {
            inputCount?.trim()?.toInt() ?: 0
        }catch (e : Exception){
            0
        }
    }

    private fun validateInput(name : String, count : Int) : Boolean{
        var result =true
        if (name.isBlank()){
            _errorInputName.value=true
            result =false
        }
        if(count <=0){
            _errorInputCount.value=true
            result= false
        }
        return result
    }

    fun resetErrorInputName(){
        _errorInputName.value=false
    }
    fun resetErrorInputCount(){
        _errorInputCount.value=false
    }
}