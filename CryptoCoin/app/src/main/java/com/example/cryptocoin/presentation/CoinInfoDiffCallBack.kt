package com.example.cryptocoin.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptocoin.domain.model.CoinPriceInfo

class CoinInfoDiffCallBack : DiffUtil.ItemCallback<CoinPriceInfo>() {
    override fun areItemsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        return oldItem == newItem
    }


}