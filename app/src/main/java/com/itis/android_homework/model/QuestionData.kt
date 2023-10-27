package com.itis.android_homework.model

data class QuestionData(
    val question: String,
    var answers: List<AnswerOptions>,
    var isChecked: Boolean = false
)