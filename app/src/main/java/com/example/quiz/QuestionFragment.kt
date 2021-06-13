package com.example.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.databinding.FragmentQuestionBinding
import com.example.quiz.model.Question

/**
 * A fragment representing a list of Items.
 */
class QuestionFragment : Fragment(R.layout.fragment_question) {

    private lateinit var binding : FragmentQuestionBinding
    private val viewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.enteredQuestionText().observe(viewLifecycleOwner, Observer{
            binding.questionEditText.setText(it)
        })

        viewModel.enteredAnswerText().observe(viewLifecycleOwner, Observer{
            binding.answerEditText.setText(it)
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = QuestionRecyclerViewAdapter(viewModel.questions)
        binding.recyclerView.adapter = adapter

        val numQuestionsRemaining =  4 - viewModel.questions.size
        binding.questionsRemaining.text = "Enter $numQuestionsRemaining More questions to begin!"

        binding.addButton.setOnClickListener {
            val question = Question(binding.questionEditText.text.toString(), binding.answerEditText.text.toString())
            viewModel.questions.add(question)
            viewModel.clearInputs()
            adapter.setQuestions(viewModel.questions)
            val numQuestionsRemaining =  4 - viewModel.questions.size
            binding.questionsRemaining.text = "Enter $numQuestionsRemaining More questions to begin!"
            if(numQuestionsRemaining == 0){
                viewModel.chosenAnswers = viewModel.questions.map{it.answer}.toMutableList().apply{shuffle()}
                findNavController().navigate(R.id.action_questionFragment_to_gameFragment)
            }
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveInputs(binding.questionEditText.text.toString(), binding.answerEditText.text.toString())
    }

}