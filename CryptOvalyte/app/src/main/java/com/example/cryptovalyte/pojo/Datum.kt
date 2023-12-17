package com.example.cryptovalyte.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Datum(
    @SerializedName("CoinInfo" ) var CoinInfo : CoinInfo? = CoinInfo()
)