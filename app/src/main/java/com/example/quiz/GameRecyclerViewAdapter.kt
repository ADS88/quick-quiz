package com.example.quiz

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

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
        holder.questionTextView.text = question.question
        holder.questionNumberTextView.text = "${viewPosition + 1})"
        holder.answers.adapter = ArrayAdapter(holder.answers.context,
                R.layout.support_simple_spinner_dropdown_item,
                possibleAnswers
        )

        val userSelectedItemIndex = possibleAnswers.indexOf(selectedAnswers[viewPosition])
        holder.answers.setSelection(userSelectedItemIndex)

        holder.answers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
    }

    override fun getItemCount(): Int = questions.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionTextView: TextView = view.findViewById(R.id.question)
        val answers: Spinner = view.findViewById(R.id.spinner)
        val questionNumberTextView: TextView = view.findViewById(R.id.question_number)
    }
}