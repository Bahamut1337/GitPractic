package com.example.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue : Int,
    val minCountOfRightAnswer : Int,
    val minParcentOfRightAnswer : Int,
    val gameTime : Int
) : Parcelable{

}