package com.example.shoppinglist.Presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.Domain.ShopItem
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemDisabledBinding
import com.example.shoppinglist.databinding.ItemEnabledBinding

class ShopListAdapter : ListAdapter<ShopItem,ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener : ((ShopItem) -> Unit)? = null
    var onShopItemClickListener : ((ShopItem) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType){
            VIEW_ENABLED -> R.layout.item_enabled
            VIEW_DISABLED -> R.layout.item_disabled
            else -> throw RuntimeException("Unknown view type : $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent, false
        )

        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),layout,parent,false)
        return ShopItemViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = viewHolder.binding

        when(binding){
            is ItemDisabledBinding -> {
                binding.name.text = shopItem.name
                binding.count.text = shopItem.count.toString()
            }
            is ItemEnabledBinding -> {
                binding.name.text = shopItem.name
                binding.count.text = shopItem.count.toString()
            }
        }

        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener{
            onShopItemClickListener?.invoke(shopItem)
        }
    /*    if(!shopItem.enabled){
            viewHolder.name.text = shopItem.name + " " + status
            viewHolder.count.text = shopItem.count.toString()
            viewHolder.name.setTextColor(ContextCompat.getColor(viewHolder.view.context, android.R.color.holo_red_dark))
        }*/
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        val item = if(shopItem.enabled){
            VIEW_ENABLED
        }else{
            VIEW_DISABLED
        }
        return item
    }


    companion object{
        const val VIEW_ENABLED = 100
        const val VIEW_DISABLED = 101

        const val MAX_PULL_SIZE = 15
    }

    //Если мы переиспользуем наш view  (viewHolder) и что бы не было бага где какой-то старый утсановленный элемент сохранился в новом view то тут мы может прописать стартовый элемент по умолчанию
    //Тут мы установили значения по умолчанию и можно не писать блок else выше
    /*override fun onViewRecycled(holder: ShopItemViewHolder) {
        holder.name.text = ""
        holder.count.text = ""
        holder.name.setTextColor(ContextCompat.getColor(holder.view.context, android.R.color.white))
    }*/
}