package com.example.cryptocoin.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.cryptocoin.domain.model.CoinPriceInfo
import com.example.cryptocoin.R
import com.example.cryptocoin.databinding.ItemCoinInfoBinding



class CoinInfoAdapter(
    private val context: Context,
    private val listener: (CoinPriceInfo) -> Unit,
)  : ListAdapter<CoinPriceInfo,CoinInfoViewHolder>(CoinInfoDiffCallBack()) {


    var onCoinClickListener  : OnCoinClickListener ?= null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)//listCoinInfo.get(position)

            with(coin) {
                val symlosTempalte = context.resources.getString(R.string.symbols_template)
                val lastUpadreTemplate = context.resources.getString(R.string.last_update_template)
                holder.tvSymbols.text = String.format(symlosTempalte,fromSymbol,toSymbol)
                holder.tvPrice.text = price.toString()
                holder.tvLastTime.text = String.format(lastUpadreTemplate,
                    lastUpdate
                )
                Glide.with(holder.itemView.context).load(imageUrl).circleCrop() // Отрисовка фотографии пользователя с помощью библиотеки Glide
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.tvImage)

                holder.itemView.setOnClickListener{
                    onCoinClickListener?.onCoinClick(this)
                }
                //Picasso.get().load(getFullImageUrl()).into(holder.tvImage)

        }



    }

    interface OnCoinClickListener{
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}