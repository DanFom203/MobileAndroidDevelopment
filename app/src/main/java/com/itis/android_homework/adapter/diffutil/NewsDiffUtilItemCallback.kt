package com.itis.android_homework.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.itis.android_homework.model.NewsDataModel


class NewsDiffUtilItemCallback : DiffUtil.ItemCallback<NewsDataModel>() {

    override fun areItemsTheSame(oldItem: NewsDataModel, newItem: NewsDataModel): Boolean {
        return oldItem.newsId == newItem.newsId
    }

    override fun areContentsTheSame(oldItem: NewsDataModel, newItem: NewsDataModel): Boolean {
        return oldItem.isLiked == newItem.isLiked
    }
}