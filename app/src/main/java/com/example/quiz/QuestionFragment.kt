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
import com.example.quiz.model.Question

/**
 * A fragment representing a list of Items.
 */
class QuestionFragment : Fragment() {

    lateinit var questionEditText: EditText
    lateinit var answerEditText: EditText
    lateinit var questionsRemaining : TextView
    private val viewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_question, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)

        questionEditText = root.findViewById(R.id.question_edit_text)
        viewModel.enteredQuestionText().observe(viewLifecycleOwner, Observer{
            questionEditText.setText(it)
        })

        answerEditText = root.findViewById(R.id.answer_edit_text)
        viewModel.enteredAnswerText().observe(viewLifecycleOwner, Observer{
            answerEditText.setText(it)
        })

        questionsRemaining = root.findViewById(R.id.questions_remaining)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = QuestionRecyclerViewAdapter(viewModel.questions)
        recyclerView.adapter = adapter

        val numQuestionsRemaining =  4 - viewModel.questions.size
        questionsRemaining.text = "Enter $numQuestionsRemaining More questions to begin!"

        root.findViewById<Button>(R.id.add_button).setOnClickListener {
            val question = Question(questionEditText.text.toString(), answerEditText.text.toString())
            viewModel.questions.add(question)
            viewModel.clearInputs()
            adapter.setQuestions(viewModel.questions)
            val numQuestionsRemaining =  4 - viewModel.questions.size
            questionsRemaining.text = "Enter $numQuestionsRemaining More questions to begin!"
            if(numQuestionsRemaining == 0){
                viewModel.chosenAnswers = viewModel.questions.map{it.answer}.toMutableList().apply{shuffle()}
                findNavController().navigate(R.id.action_questionFragment_to_gameFragment)
            }
        }

        return root
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveInputs(questionEditText.text.toString(), answerEditText.text.toString())
    }

}