package com.example.composition.Presentation

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.Domain.Entity.GameResult
import com.example.composition.Domain.Entity.Level
import com.example.composition.Presentation.GameFinishedFragment.Companion.KEY_GAME_RESULT
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    //Переменный созданные через by lazy будут инициализированы только после обращения к ним что бы не было багов и прочей хуйни
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(
                args.level,
                requireActivity().application
            )
        ).get(GameViewModel::class.java)
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding ?= null
    private val binding : FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListeners()

    }

    private fun setClickListeners(){
        for(tvOption in tvOptions){
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }


    private fun observeViewModel(){
        viewModel.question.observe(viewLifecycleOwner){
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNubmer.toString()
            for(i in 0 until tvOptions.size){
                tvOptions[i].text = it.option[i].toString()
            }
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it,true)
        }

        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.setTextColor(setColor(it))
        }
        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner){
            val color = setColor(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formatedTime.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinishedFragment(it)
        }
        viewModel.progresAnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }
    }

    private fun setColor(it: Boolean): Int {
        val colorResult = if (it) R.color.green else R.color.red
        val color = ContextCompat.getColor(requireActivity(), colorResult)
        return color
    }



    private fun launchGameFinishedFragment(gameResult: GameResult){
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult))

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{

        const val CHOOSE_LEVEL = "chose_level"
        const val NAME_GAME = "name_game"

        fun newInstance(level: Level) : GameFragment{
            return GameFragment().apply { arguments = Bundle().apply {
                    putParcelable(CHOOSE_LEVEL,level)
                }
            }
        }
    }

}