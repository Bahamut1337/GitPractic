package com.example.shoppinglist.Presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.Domain.ShopItem
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditFinishListener {

   lateinit var binding: ActivityShopItemBinding
    var shopItemId = ShopItem.UNDEFINED_ID
    var screenMode = MODE_UNKNOW


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pareseIntent()
        if(savedInstanceState == null){
            launchRightMode()
        }
    }

    private fun pareseIntent(){
        if(!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("pizda1")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if(mode != MODE_CHANGE && mode != MODE_ADD){
            throw RuntimeException("pizda2")
        }
       screenMode = mode
        if(screenMode == MODE_CHANGE){
            if(!intent.hasExtra(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("pizda3")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID,-1)
        }
    }


    private fun launchRightMode(){
        val fragment = when(screenMode){
            MODE_CHANGE ->ShopItemFragment.newInstanceChangeItem(shopItemId)
            MODE_ADD ->ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("popa!")
        }
        supportFragmentManager.beginTransaction().replace(R.id.Shop_item_container,fragment).commit()
    }

    override fun onEditFinish() {
        Toast.makeText(this@ShopItemActivity,"Success", Toast.LENGTH_SHORT).show()
        onBackPressedDispatcher.onBackPressed()
    }

    companion object{

        private const val EXTRA_SCREEN_MODE = "extra_mod"
        private const val EXTRA_SHOP_ITEM_ID = "extra_item_id"
        private const val MODE_CHANGE = "mode_change"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOW = ""

        fun newIntentAdd(context: Context) : Intent{
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentChange(context: Context, shopItemId : Int) : Intent{
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_CHANGE)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}