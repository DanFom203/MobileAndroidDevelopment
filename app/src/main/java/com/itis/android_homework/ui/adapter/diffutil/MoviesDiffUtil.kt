package com.itis.android_homework.ui.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.itis.android_homework.model.MovieModel

class MoviesDiffUtil(
    private val oldItemsList: List<MovieModel>,
    private val newItemsList: List<MovieModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItemsList.size

    override fun getNewListSize(): Int = newItemsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        return (oldItem.title == newItem.title) &&
                (oldItem.year == newItem.year)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        return if (oldItem.isLiked != newItem.isLiked) {
            newItem.isLiked
        } else {
            super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}