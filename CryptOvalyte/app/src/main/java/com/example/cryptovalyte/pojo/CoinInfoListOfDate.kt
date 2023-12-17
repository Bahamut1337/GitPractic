package com.example.cryptovalyte.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class CoinInfoListOfDate (
    @SerializedName("Data")
    @Expose
    val data: List<Datum>? = null
)