package com.example.shoppinglist.Presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.Domain.ShopItem
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding

class ShopItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityShopItemBinding
    lateinit var viewModel: ShopItemViewModel
    var shopItemId = ShopItem.UNDEFINED_ID
    var screenMode = MODE_UNKNOW


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pareseIntent()
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)

        addTextChangeListeners()
        launchRightMode()
        observeViewModel()


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
    private fun observeViewModel(){
        viewModel.errorInputName.observe(this){
            val message =if(it){
                getString(R.string.error_name)
            }else{
                null
            }

            binding.textInputLayoutNAME.error = message
        }
        viewModel.errorInputCount.observe(this){
            val message =if(it){
                getString(R.string.error_count)
            }else{
                null
            }

            binding.textInputLayoutCOUNT.error = message
        }
        viewModel.shouldCloseScreen.observe(this){
            finish()
        }
    }

    private fun launchRightMode(){
        when(screenMode){
            MODE_CHANGE ->launchChangeForm()
            MODE_ADD ->launchAddForm()
        }
    }

    private fun addTextChangeListeners(){
        binding.editName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editCount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun launchChangeForm() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this){
            binding.editName.setText(it.name)
            binding.editCount.setText(it.count.toString())
        }
        binding.addBtn.setOnClickListener {
            viewModel.changeShopItem(binding.editName.text?.toString(),binding.editCount.text?.toString())
        }
    }

    private fun launchAddForm(){
        binding.addBtn.setOnClickListener {
            viewModel.addShopItem(binding.editName.text?.toString(), binding.editCount.text?.toString())
        }

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