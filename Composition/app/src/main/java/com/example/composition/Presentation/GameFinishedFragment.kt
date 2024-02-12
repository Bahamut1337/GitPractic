package com.example.composition.Presentation

import android.os.Build
import android.os.Bundle
import android.util.Pair
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import com.example.composition.Domain.Entity.GameResult
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult : GameResult

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding ?= null
    private val binding : FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameResult = args.gameResult
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameFinishedBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setResult()


        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun setResult() {
        binding.gameResult = args.gameResult

    }

    private fun getPercentOfRightAnswers() : String{
        return  ((gameResult.countOfRightAnswers/gameResult.countOfQuestions.toDouble())*100).toInt().toString()
    }




    private fun retryGame(){
        findNavController().popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object{

        const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult) : GameFinishedFragment{
            return GameFinishedFragment().apply { arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT,gameResult)
                }
            }
        }
    }
}