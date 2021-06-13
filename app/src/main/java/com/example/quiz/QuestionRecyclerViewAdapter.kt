package com.example.quiz

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

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
        holder.questionTextView.text = question.question
        holder.answerTextView.text = question.answer
    }

    override fun getItemCount(): Int = questions.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionTextView: TextView = view.findViewById(R.id.question_text_view)
        val answerTextView: TextView = view.findViewById(R.id.answer_text_view)
    }

    fun setQuestions(newQuestions: List<Question>){
        questions = newQuestions
        notifyDataSetChanged()
    }
}