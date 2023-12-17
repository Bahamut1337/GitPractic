package com.example.cryptovalyte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptovalyte.adapters.CoininfoAdapter
import com.example.cryptovalyte.databinding.ActivityCoinPriceListBinding
import com.example.cryptovalyte.pojo.CoinPriceInfo


class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel : CoinViewModel


    private lateinit var binding: ActivityCoinPriceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoininfoAdapter(this){}

        adapter.onCoinClickListener = object: CoininfoAdapter.OnCoinClickListener{
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity, coinPriceInfo.fromsymbol)
                startActivity(intent)
            }
        }

        binding.rvCoinPriceList.adapter = adapter

        viewModel =ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[CoinViewModel::class.java]
        viewModel.priceList.observe(this) {
           adapter.listCoinInfo = it
           Log.d("MyLog", it.toString())
        }


    }


}


