package com.example.composition.Domain.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winner : Boolean,
    val countOfRightAnswers : Int,
    val countOfQuestions : Int,
    val gameSettings: GameSettings
) : Parcelable{

    val countOfRightAnswersString : String get() = countOfRightAnswers.toString()

    val percentOfRightAnswers : String get() = ((countOfRightAnswers/countOfQuestions.toDouble())*100).toString()
}