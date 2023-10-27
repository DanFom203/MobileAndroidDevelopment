package com.itis.android_homework.holder

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.databinding.ItemAnswerBinding
import com.itis.android_homework.model.AnswerOptions

class AnswerHolder (
    private val viewBinding: ItemAnswerBinding,
    private val onItemChecked: (Int) -> Unit,
    private val onRootClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {

    init {
        viewBinding.answerRb.setOnClickListener {
            onItemChecked.invoke(adapterPosition)
        }
        viewBinding.root.setOnClickListener {
            onRootClicked.invoke(adapterPosition)
        }
    }

    fun bindItem(answer: AnswerOptions, itemsCount: Int) {
        with(viewBinding) {
            answerTv.text = answer.answer
            answerRb.isChecked = answer.isSelected
            answerRb.isEnabled = !answer.isSelected
            root.foreground = if (answer.isSelected) {
                null
            } else {
                ColorDrawable(Color.TRANSPARENT)
            }
            divider.isVisible = adapterPosition != itemsCount - 1
        }
    }
}

