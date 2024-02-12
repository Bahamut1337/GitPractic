package com.example.composition.Domain.Entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue : Int,
    val minCountOfRightAnswer : Int,
    val minParcentOfRightAnswer : Int,
    val gameTime : Int
) : Parcelable{

    val minCountOfRightAnswerString : String get() = minCountOfRightAnswer.toString()
    val minParcentOfRightAnswerString : String get() = minParcentOfRightAnswer.toString()

}