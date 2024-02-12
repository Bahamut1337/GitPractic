package com.example.composition.Presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.Data.GameRepositoryImpl
import com.example.composition.Domain.Entity.GameResult
import com.example.composition.Domain.Entity.GameSettings
import com.example.composition.Domain.Entity.Level
import com.example.composition.Domain.Entity.Question
import com.example.composition.Domain.UseCases.GenerateQuestionUseCase
import com.example.composition.Domain.UseCases.GetGameSettingsUseCase
import com.example.composition.R

class GameViewModel(
    private val application: Application,
    private val level: Level
) : ViewModel() {

    lateinit var gameSettings: GameSettings

    val repository = GameRepositoryImpl
    var timer : CountDownTimer ?= null


    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _formatedTime = MutableLiveData<String>()
    val formatedTime : LiveData<String> get() = _formatedTime

    private val _question = MutableLiveData<Question>()
    val question : LiveData<Question> get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers : LiveData<Int> get() = _percentOfRightAnswers

    private val _progresAnswers = MutableLiveData<String>()
    val progresAnswers : LiveData<String> get() = _progresAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers : LiveData<Boolean> get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers : LiveData<Boolean> get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent : LiveData<Int> get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult : LiveData<GameResult> get() = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestion = 0


    init {
        startGame()
    }

    fun startGame(){
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress(){
        val percent = calculatePercent()
        _percentOfRightAnswers.value = percent
        _progresAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswer
        )

        _enoughCountOfRightAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswer
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minParcentOfRightAnswer
    }

    private fun calculatePercent():Int{
        if(countOfQuestion == 0){
            return 0
        }
        return ((countOfRightAnswers/countOfQuestion.toDouble())*100).toInt()
    }

    private fun checkAnswer(number: Int){
        val rightAnswer = question.value?.rightAnswer
        if(number == rightAnswer){
            countOfRightAnswers++
        }
        countOfQuestion++
    }

    private fun getGameSettings(){
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minParcentOfRightAnswer
    }

    private fun startTimer(){
        timer = object : CountDownTimer(
            gameSettings.gameTime* MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ){
            override fun onTick(millisUntilFinished: Long) {
                _formatedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion(){
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formatTime(millisUntilFinished: Long) : String{
        val seconds = millisUntilFinished/ MILLIS_IN_SECONDS
        val minutes = seconds/ SECONDS_IN_MINUTS
        val leftSeconds = seconds - (minutes* SECONDS_IN_MINUTS)
        return String.format("%02d:%02d",minutes,leftSeconds)
    }

    private fun finishGame(){
        _gameResult.value = GameResult(
            winner = _enoughCountOfRightAnswers.value == true && _enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestion,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object{
        const val MILLIS_IN_SECONDS = 1000L
        const val SECONDS_IN_MINUTS = 60L
    }
}