package com.itis.android_homework.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.R
import com.itis.android_homework.databinding.ItemMovieVerticalBinding
import com.itis.android_homework.model.MovieModel

class MoviesViewHolder(
    private val viewBinding: ItemMovieVerticalBinding,
    private val onMovieClicked: ((MovieModel) -> Unit),
    private val onLikeClicked: ((Int, MovieModel) -> Unit),
) : RecyclerView.ViewHolder(viewBinding.root) {
    private var item: MovieModel? = null

    init {
        viewBinding.root.setOnClickListener {
            this.item?.let(onMovieClicked)
        }
        viewBinding.likeBtnIv.setOnClickListener {
            this.item?.let {
                val data = it.copy(isLiked = !it.isLiked)
                onLikeClicked(adapterPosition, data)
            }
        }
    }

    fun bindItem(item: MovieModel) {
        this.item = item
        with(viewBinding) {
            tvTitle.text = item.title
            item.movieImage?.let { res ->
                ivImage.setImageResource(res)
            }
            changeLikeBtnStatus(isChecked = item.isLiked)
        }
    }

    fun changeLikeBtnStatus(isChecked: Boolean) {
        val likeDrawable = if (isChecked) R.drawable.ic_thumb_up_alt_red_24 else R.drawable.ic_thumb_up_alt_white_24
        viewBinding.likeBtnIv.setImageResource(likeDrawable)
    }
}