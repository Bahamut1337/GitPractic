package com.example.shoppinglist.Presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.Domain.ShopItem
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.FragmentShopItemBinding


class ShopItemFragment: Fragment(){

    lateinit var viewModel: ShopItemViewModel

    var screenMode : String = MODE_UNKNOW
    var shopItemId : Int= ShopItem.UNDEFINED_ID
    private lateinit var binding: FragmentShopItemBinding
    private lateinit var onEditFinishListener : OnEditFinishListener

    override fun onAttach(context: Context) {//тут context является той самой активити к которой прикрелпен фрагмент
        super.onAttach(context)
        if(context is OnEditFinishListener){//Поэтому мы тут делаем привидение типа
            onEditFinishListener = context
        }else{//Реализация интерфейса в активи является обезательной если с помощью этого интерфейса мы передаем запрос с фрагмента на активити, в случае если реализация в классе активити была забыта мы выбросим исключение
            throw RuntimeException("Activity must implement OnEditFinishListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pareseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopItemBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }


    private fun pareseParams(){
        val args = requireArguments()
        if(!args.containsKey(SCREEN_MODE)){
            throw RuntimeException("pizda1")
        }
        val mode = args.getString(SCREEN_MODE)
        if(mode != MODE_CHANGE && mode != MODE_ADD){
            throw RuntimeException("pizda2")
        }
        screenMode = mode
        if(screenMode == MODE_CHANGE){
            if(!args.containsKey(SHOP_ITEM_ID)){
                throw RuntimeException("pizda3")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID,ShopItem.UNDEFINED_ID)
        }
    }
    private fun observeViewModel(){
        viewModel.errorInputName.observe(viewLifecycleOwner){
            val message =if(it){
                getString(R.string.error_name)
            }else{
                null
            }

            binding.textInputLayoutNAME.error = message
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner){
            val message =if(it){
                getString(R.string.error_count)
            }else{
                null
            }

            binding.textInputLayoutCOUNT.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner){
            onEditFinishListener?.onEditFinish()
        }
    }

    private fun launchRightMode(){
        when(screenMode){
            MODE_CHANGE ->launchChangeForm()
            MODE_ADD ->launchAddForm()
        }
    }

    private fun addTextChangeListeners(){
        binding.editName.addTextChangedListener(object : TextWatcher {
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
        viewModel.shopItem.observe(viewLifecycleOwner){
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


    interface OnEditFinishListener {
        fun onEditFinish()
    }

    companion object{

        private const val SCREEN_MODE = "extra_mod"
        private const val SHOP_ITEM_ID = "extra_item_id"
        private const val MODE_CHANGE = "mode_change"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOW = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                    arguments = Bundle().apply {
                        putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }
        fun newInstanceChangeItem(shopItemId: Int) : ShopItemFragment{
            var arg = Bundle()
            arg.apply { putString(SCREEN_MODE, MODE_CHANGE)
                putInt(SHOP_ITEM_ID,shopItemId)
            }
            val fragment = ShopItemFragment()
            fragment.arguments = arg
            return fragment
        }
    }
}