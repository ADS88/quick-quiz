package com.example.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quiz.model.Question

class SharedViewModel: ViewModel() {

    var questions = mutableListOf<Question>()

    private val _enteredQuestionText = MutableLiveData<String>("")
    fun enteredQuestionText(): LiveData<String> = _enteredQuestionText

    private val _enteredAnswerText = MutableLiveData<String>("")
    fun enteredAnswerText(): LiveData<String> = _enteredAnswerText


    fun saveInputs(question: String, answer: String){
        _enteredQuestionText.value = question
        _enteredAnswerText.value = answer
    }

    fun clearInputs(){
        _enteredAnswerText.value = ""
        _enteredQuestionText.value = ""
    }

    fun newGame(){
        questions = mutableListOf()
    }

    var chosenAnswers = mutableListOf<String>()

}