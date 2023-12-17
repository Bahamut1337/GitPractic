package com.example.cryptovalyte

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cryptovalyte.databinding.ActivityCoinDetailBinding


class CoinDetailActivity: AppCompatActivity() {

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
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol!!).observe(this, Observer {



            binding.priceInt.text = it.price.toString()
            binding.minPriceInt.text = it.lowday.toString()
            binding.maxPriceInt.text = it.highday.toString()
            binding.lastOrderInt.text = it.lastmarket
            binding.reloadedInt.text = it.getFormmatedTime()

            val frms= it.fromsymbol
            val tms= it.tosymbol
            val str = "$frms / $tms"
            binding.tvSymbols.text = str

            Glide.with(binding.root).load(it.getFullImageUrl()).circleCrop() // Отрисовка фотографии пользователя с помощью библиотеки Glide
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.tvImageDetail)
        })

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