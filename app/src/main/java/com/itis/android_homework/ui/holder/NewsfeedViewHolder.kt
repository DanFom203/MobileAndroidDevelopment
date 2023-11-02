package com.itis.android_homework.ui.holder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.R
import com.itis.android_homework.databinding.ItemNewsfeedCvBinding
import com.itis.android_homework.model.NewsDataModel

class  NewsfeedViewHolder(
    private val viewBinding: ItemNewsfeedCvBinding,
    private val onNewsClicked: ((NewsDataModel) -> Unit),
    private val onLikeClicked: ((Int, NewsDataModel) -> Unit),
) : RecyclerView.ViewHolder(viewBinding.root) {
    private val dateSeparatorCardView: CardView = viewBinding.root.findViewById(R.id.date_cv)

    private var item: NewsDataModel? = null

    init {
        viewBinding.root.setOnClickListener {
            this.item?.let(onNewsClicked)
        }
        viewBinding.likeBtnIv.setOnClickListener {
            this.item?.let {
                item!!.isLiked = !item!!.isLiked
                val data = it.copy()
                onLikeClicked(adapterPosition, data)
            }
        }
    }

    fun bindItem(item: NewsDataModel) {
        this.item = item
        with(viewBinding) {
            newsTitleTv.text = item.newsTitle
            item.newsDetails?.let { newsDetailsTv.text = it }
            item.newsImage?.let { res ->
                newsImageIv.setImageResource(res)
            }
            changeLikeBtnStatus(isChecked = item.isLiked)
        }
    }

    fun showDateSeparator(countOfSeparators : Int) {
        dateSeparatorCardView.visibility = View.VISIBLE
        when (countOfSeparators) {
            0 -> viewBinding.dateTv.setText(R.string.today)
            1 -> viewBinding.dateTv.setText(R.string.yesterday)
            2 -> viewBinding.dateTv.setText(R.string.two_days_ago)
            3 -> viewBinding.dateTv.setText(R.string.three_days_ago)
            4 -> viewBinding.dateTv.setText(R.string.four_days_ago)
            5 -> viewBinding.dateTv.setText(R.string.five_days_ago)
            6 -> viewBinding.dateTv.setText(R.string.six_days_ago)
        }
    }

    fun hideDateSeparator() {
        dateSeparatorCardView.visibility = View.GONE
    }


    fun changeLikeBtnStatus(isChecked: Boolean) {
        val likeDrawable = if (isChecked) R.drawable.ic_thumb_up_alt_red_24 else R.drawable.ic_thumb_up_alt_white_24
        viewBinding.likeBtnIv.setImageResource(likeDrawable)
    }
}