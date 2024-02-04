package com.example.composition.Data

import android.graphics.BitmapFactory.Options
import com.example.composition.Domain.Entity.GameSettings
import com.example.composition.Domain.Entity.Level
import com.example.composition.Domain.Entity.Question
import com.example.composition.Domain.Repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE,maxSumValue+1)
        val visibleNumbers = Random.nextInt(MIN_ANSWER_VALUE,sum-1)
        val option = HashSet<Int>()
        val rightAnswer = sum-visibleNumbers
        option.add(rightAnswer)
        val from = max( rightAnswer-countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue-1,rightAnswer+countOfOptions)
        while (option.size<countOfOptions){
            option.add(Random.nextInt(from,to))
        }
        return Question(sum,visibleNumbers,option.toList())

    }

    override fun getSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST -> GameSettings(
                10,
                3,
                50,
                10
            )
            Level.EASY -> GameSettings(
                10,
                5,
                50,
                60
            )
            Level.NORMAL -> GameSettings(
                15,
                10,
                70,
                60
            )
            Level.HARD -> GameSettings(
                20,
                20,
                90,
                100
            )
        }
    }
}