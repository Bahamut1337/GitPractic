package com.example.cryptovalyte.adapters

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptovalyte.R

import com.example.cryptovalyte.databinding.ItemCoinInfoBinding

import com.example.cryptovalyte.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoininfoAdapter(
    private val context: Context,
    private val listener: (CoinPriceInfo) -> Unit,
)  : RecyclerView.Adapter<CoininfoAdapter.CoinInfoViewHolder>() {

    var listCoinInfo : List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener  : OnCoinClickListener ?= null

    inner class CoinInfoViewHolder(val binding: ItemCoinInfoBinding):RecyclerView.ViewHolder(binding.root){
        val tvImage = binding.tvImage
        val tvPrice = binding.tvPrice
        val tvLastTime = binding.tvLastTime
        val tvSymbols = binding.tvSymbols
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(binding)
    }

    override fun getItemCount() = listCoinInfo.size


    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = listCoinInfo[position]//listCoinInfo.get(position)

        with(holder){
            with(coin) {
                val symlosTempalte = context.resources.getString(R.string.symbols_template)
                val lastUpadreTemplate = context.resources.getString(R.string.last_update_template)
                binding.tvSymbols.text = String.format(symlosTempalte,fromsymbol,tosymbol)
                binding.tvPrice.text = price.toString()
                binding.tvLastTime.text = String.format(lastUpadreTemplate,getFormmatedTime())
                Glide.with(itemView.context).load(getFullImageUrl()).circleCrop() // Отрисовка фотографии пользователя с помощью библиотеки Glide
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.tvImage)

                itemView.setOnClickListener{
                    onCoinClickListener?.onCoinClick(this)
                }
                //Picasso.get().load(getFullImageUrl()).into(holder.tvImage)
            }
        }



    }

    interface OnCoinClickListener{
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }

}