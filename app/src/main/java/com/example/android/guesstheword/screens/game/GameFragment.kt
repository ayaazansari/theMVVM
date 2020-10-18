package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )
        Log.i("GameFragment", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // update the UI
        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
            updateWordText()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
            updateWordText()
        }

        viewModel.score.observe(this, Observer {newScore->
            binding.scoreText.text = newScore.toString()
        })

        viewModel.eventGameFinish.observe(this, Observer { hasFinished->
           if(hasFinished) {
               gameFinished()
               viewModel.onGameFinishComplete()
           }
        })
        updateWordText()
        return binding.root

    }
    private fun gameFinished() {
//        val action = GameFragmentDirections.actionGameToScore()
//        val currentScore =viewModel.score.value?:0
//        action.se(currentScore)
////
//        findNavController(this).navigate(action)
        Toast.makeText(this.activity ,"Game has finished",Toast.LENGTH_SHORT).show()
    }

    private fun updateWordText() {
        binding.wordText.text = viewModel.word
    }


}
