package com.itis.android_homework.adapter.diffutil

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.databinding.ItemNewsfeedCvBinding
import com.itis.android_homework.model.NewsDataModel
import com.itis.android_homework.ui.holder.NewsfeedViewHolder
import kotlin.random.Random

class NewsAdapter(
    var newsList: MutableList<NewsDataModel>,
    private val onNewsClicked: ((NewsDataModel) -> Unit),
    private val onLikeClicked: ((Int, NewsDataModel) -> Unit),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var countOfSeparators : Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsfeedViewHolder(
            viewBinding = ItemNewsfeedCvBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onNewsClicked = onNewsClicked,
            onLikeClicked = onLikeClicked,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? NewsfeedViewHolder)?.bindItem(item = newsList[position])

        if (position == 0) {
            (holder as? NewsfeedViewHolder)?.showDateSeparator(countOfSeparators)
            countOfSeparators++
        }
        if ((position) % 8 == 0) {
            (holder as? NewsfeedViewHolder)?.showDateSeparator(countOfSeparators)
            countOfSeparators++
        } else {
            (holder as? NewsfeedViewHolder)?.hideDateSeparator()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            (payloads.first() as? Boolean)?.let {
                (holder as? NewsfeedViewHolder)?.changeLikeBtnStatus(it)
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = newsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: MutableList<NewsDataModel>) {
        //val diff = NewsDiffUtil(oldItemsList = newsList, newItemsList = list)
        //val diffResult = DiffUtil.calculateDiff(diff)
        //newsList.clear()
        //newsList.addAll(list)
        list.addAll(this.newsList)
        newsList.clear()
        repeat(list.size) {
            val newIndex = Random.nextInt(0, list.size)
            newsList.add(list[newIndex])
        }
        //diffResult.dispatchUpdatesTo(this)
    }



    fun updateItem(position: Int, item: NewsDataModel) {
        this.newsList[position] = item
        notifyItemChanged(position, item.isLiked)
    }
}