package com.example.quiz

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.quiz.databinding.EntryListItemBinding

import com.example.quiz.model.Question

class QuestionRecyclerViewAdapter(
    private var questions: List<Question>
) : RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.entry_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        with(holder.binding){
            questionTextView.text = question.question
            answerTextView.text = question.answer
        }
    }

    override fun getItemCount(): Int = questions.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = EntryListItemBinding.bind(view)
    }

    fun setQuestions(newQuestions: List<Question>){
        questions = newQuestions
        notifyDataSetChanged()
    }
}