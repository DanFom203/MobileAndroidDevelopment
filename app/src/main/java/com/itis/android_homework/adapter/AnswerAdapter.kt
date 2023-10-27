package com.itis.android_homework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.databinding.ItemAnswerBinding
import com.itis.android_homework.holder.AnswerHolder
import com.itis.android_homework.model.AnswerOptions

class AnswerAdapter (
    val items: List<AnswerOptions>,
    private val onItemChecked: ((Int) -> Unit),
    private val onRootClicked: ((Int) -> Unit),
) : RecyclerView.Adapter<AnswerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        return AnswerHolder(
            viewBinding = ItemAnswerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemChecked = onItemChecked,
            onRootClicked = onRootClicked,
        )
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        holder.bindItem(answer = items[position], itemCount)
    }


    override fun getItemCount(): Int = items.count()
}