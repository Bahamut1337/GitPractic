package com.example.cryptocoin.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocoin.databinding.ItemCoinInfoBinding

class CoinInfoViewHolder(val binding: ItemCoinInfoBinding): RecyclerView.ViewHolder(binding.root){
    val tvImage = binding.tvImage
    val tvPrice = binding.tvPrice
    val tvLastTime = binding.tvLastTime
    val tvSymbols = binding.tvSymbols
}