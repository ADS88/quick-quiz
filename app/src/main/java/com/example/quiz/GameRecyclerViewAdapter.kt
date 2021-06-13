package com.example.quiz

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.quiz.databinding.GameListItemBinding

import com.example.quiz.model.Question

class GameRecyclerViewAdapter(
        private var questions: List<Question>,
        private val selectedAnswers: MutableList<String>
) : RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder>() {

    val possibleAnswers = questions.map { it.answer }.toMutableList().apply{shuffle()}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.game_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, viewPosition: Int) {
        val question = questions[viewPosition]
        val userSelectedItemIndex = possibleAnswers.indexOf(selectedAnswers[viewPosition])

        val onSpinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                selectedAnswers[viewPosition] = possibleAnswers[position]
            }
        }

        with(holder.binding){
            questionText.text = question.question
            questionNumber.text = "${viewPosition + 1})"
            spinner.adapter = ArrayAdapter(holder.binding.spinner.context,
                    R.layout.support_simple_spinner_dropdown_item,
                    possibleAnswers
            )
            spinner.setSelection(userSelectedItemIndex)
            spinner.onItemSelectedListener = onSpinnerItemSelectedListener
        }
    }

    override fun getItemCount(): Int = questions.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = GameListItemBinding.bind(view)
    }
}