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


class GameFragment : Fragment() {

    private val viewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_game, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = GameRecyclerViewAdapter(viewModel.questions, viewModel.chosenAnswers)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        root.findViewById<Button>(R.id.check_button).setOnClickListener{
            checkAnswers()
        }

        return root
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