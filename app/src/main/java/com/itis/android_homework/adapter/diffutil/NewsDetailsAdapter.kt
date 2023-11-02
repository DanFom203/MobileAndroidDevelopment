package com.itis.android_homework.adapter.diffutil

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.databinding.ItemNewsDetailsBinding
import com.itis.android_homework.model.NewsDataModel
import com.itis.android_homework.ui.holder.NewsDetailsViewHolder

class NewsDetailsAdapter (private val activity: BaseActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var newsItem : NewsDataModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsDetailsViewHolder(
            viewBinding = ItemNewsDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            activity = activity
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        newsItem?.let { (holder as? NewsDetailsViewHolder)?.bindItem(item = it) }
    }

    override fun getItemCount(): Int = 1

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(currentNewsItem: NewsDataModel) {
        newsItem = currentNewsItem
    }

}