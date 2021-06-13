package com.example.quiz

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.databinding.FragmentGameBinding


class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var binding : FragmentGameBinding
    private val viewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentGameBinding.inflate(inflater, container, false)

        binding.recyclerView.adapter = GameRecyclerViewAdapter(viewModel.questions, viewModel.chosenAnswers)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.checkButton.setOnClickListener{
            checkAnswers()
        }

        return binding.root
    }

    private fun checkAnswers(){
        val allCorrect = viewModel.questions.zip(viewModel.chosenAnswers).all{
            (question, chosenAnswer) -> question.answer == chosenAnswer
        }
        if(allCorrect){
            AlertDialog.Builder(requireContext())
                    .setMessage("You guessed all correct!")
                    .setPositiveButton("New game") { _, _ ->
                        viewModel.newGame()
                        findNavController().navigate(R.id.action_gameFragment_to_questionFragment)
                    }
                    .show()
        } else {
            AlertDialog.Builder(requireContext())
                    .setMessage("Keep guessing!")
                    .setPositiveButton("Ok") { _, _ ->
                    }
                    .show()
        }
    }

}