package com.example.cryptocoin.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocoin.domain.model.CoinPriceInfo
import com.example.cryptocoin.databinding.ActivityCoinPrceListBinding


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel : CoinViewModel


    private lateinit var binding: ActivityCoinPrceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPrceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this){}

        adapter.onCoinClickListener = object: CoinInfoAdapter.OnCoinClickListener{
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity, coinPriceInfo.fromSymbol)
                startActivity(intent)
            }
        }

        binding.rvCoinPriceList.adapter = adapter

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
            Log.d("MyLog", it.toString())
        }


    }
}
