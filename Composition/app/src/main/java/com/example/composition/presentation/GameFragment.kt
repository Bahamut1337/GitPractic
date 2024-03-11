package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Level
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

    private var _binding: FragmentGameBinding ?= null
    private val binding : FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


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
        binding.viewModel =viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
    }



    private fun observeViewModel(){
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinishedFragment(it)
        }

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