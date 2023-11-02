package com.itis.android_homework.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.itis.android_homework.model.NewsDataModel

class NewsDiffUtil(
    private val oldItemsList: List<NewsDataModel>,
    private val newItemsList: List<NewsDataModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItemsList.size

    override fun getNewListSize(): Int = newItemsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]
        return oldItem.newsId == newItem.newsId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        return (oldItem.newsTitle == newItem.newsTitle) &&
                (oldItem.newsDetails == newItem.newsDetails)
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