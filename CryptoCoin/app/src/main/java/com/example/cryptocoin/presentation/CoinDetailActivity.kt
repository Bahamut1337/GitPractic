package com.example.cryptocoin.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cryptocoin.R
import com.example.cryptocoin.databinding.ActivityCoinDetailBinding
class CoinDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCoinDetailBinding
    private lateinit var viewModel : CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(!intent.hasExtra(EXTRA_FROM_SYMBOL)){
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: ""

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(this){



            binding.priceInt.text = it.price.toString()
            binding.minPriceInt.text = it.lowDay.toString()
            binding.maxPriceInt.text = it.highDay.toString()
            binding.lastOrderInt.text = it.lastMarket
            binding.reloadedInt.text = it.lastUpdate

            val frms= it.fromSymbol
            val tms= it.toSymbol
            val str = "$frms / $tms"
            binding.tvSymbols.text = str

            Glide.with(binding.root).load(it.imageUrl).circleCrop() // Отрисовка фотографии пользователя с помощью библиотеки Glide
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.tvImageDetail)
        }

    }

    companion object{
        private const val EXTRA_FROM_SYMBOL = "fsym"

        fun newIntent(context : Context,fromSymbol : String) : Intent{
            val intent = Intent(context,CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL,fromSymbol)
            return intent
        }
    }
}
