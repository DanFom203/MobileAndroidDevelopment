package com.itis.android_homework.utils

import com.itis.android_homework.model.AnswerOptions
import com.itis.android_homework.model.QuestionData

object QARepository {
    val questions: List<QuestionData> = listOf(
        QuestionData(
    "What color is school desk in Russia?", listOf(
            AnswerOptions("Yellow"),
            AnswerOptions("Red"),
            AnswerOptions("Blue"),
            AnswerOptions("White"),
            AnswerOptions("Black"),
            AnswerOptions("Purple"),
            AnswerOptions("Orange"),
            AnswerOptions("Light blue"),
            AnswerOptions("Green")
        )
        ),
        QuestionData(
            "What color is banana?", listOf(
                AnswerOptions("Yellow"),
                AnswerOptions("Red"),
                AnswerOptions("Blue"),
                AnswerOptions("White"),
                AnswerOptions("Black"),
                AnswerOptions("Purple"),
                AnswerOptions("Orange"),
                AnswerOptions("Light blue"),
                AnswerOptions("Green")
            )
        ),
        QuestionData(
            "What color is grass?", listOf(
                AnswerOptions("Yellow"),
                AnswerOptions("Red"),
                AnswerOptions("Blue"),
                AnswerOptions("White"),
                AnswerOptions("Black"),
                AnswerOptions("Purple"),
                AnswerOptions("Orange"),
                AnswerOptions("Light blue"),
                AnswerOptions("Green")
            )
        ),
        QuestionData(
            "What color is falling snow?", listOf(
                AnswerOptions("Yellow"),
                AnswerOptions("Red"),
                AnswerOptions("Blue"),
                AnswerOptions("White"),
                AnswerOptions("Black"),
                AnswerOptions("Purple"),
                AnswerOptions("Orange"),
                AnswerOptions("Light blue"),
                AnswerOptions("Green")
            )
        ),
        QuestionData(
            "What color is clear sky?", listOf(
                AnswerOptions("Yellow"),
                AnswerOptions("Red"),
                AnswerOptions("Blue"),
                AnswerOptions("White"),
                AnswerOptions("Black"),
                AnswerOptions("Purple"),
                AnswerOptions("Orange"),
                AnswerOptions("Light blue"),
                AnswerOptions("Green")
            )
        ),
        QuestionData(
            "What color is TV screen, when it is off?", listOf(
                AnswerOptions("Yellow"),
                AnswerOptions("Red"),
                AnswerOptions("Blue"),
                AnswerOptions("White"),
                AnswerOptions("Black"),
                AnswerOptions("Purple"),
                AnswerOptions("Orange"),
                AnswerOptions("Light blue"),
                AnswerOptions("Green")
            )
        ),
    )

    fun getQuestionByPosition(position: Int): QuestionData {
        return if (position in questions.indices) {
            questions[position]
        } else {
            throw Exception()
        }
    }
}