package com.itis.android_homework.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.android_homework.R
import com.itis.android_homework.databinding.ItemMovieVerticalBinding
import com.itis.android_homework.model.MovieModel

class MoviesViewHolder(
    private val viewBinding: ItemMovieVerticalBinding,
    private val glide: RequestManager,
    private val onMovieClicked: ((MovieModel) -> Unit),
    private val onLikeClicked: ((MovieModel) -> Unit),
    private val onDeleteClicked: ((MovieModel) -> Unit)
) : RecyclerView.ViewHolder(viewBinding.root) {
    private var item: MovieModel? = null
    private var isDeleteButtonVisible: Boolean = false
    private val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    init {
        viewBinding.root.setOnClickListener {
            this.item?.let(onMovieClicked)
        }
        viewBinding.addToFavoriteBtnIv.setOnClickListener {
            this.item?.let {
                onLikeClicked(it)
            }
        }
        viewBinding.root.setOnLongClickListener {
            showDeleteButton()
            true
        }

        viewBinding.deleteMovieButton.setOnClickListener {
            this.item?.let(onDeleteClicked)
        }
    }

    fun bindItem(item: MovieModel) {
        this.item = item
        with(viewBinding) {
            tvTitle.text = item.title

            glide.load(item.imgUrl)
                .apply(options)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(ivImage)
            glide.load(R.drawable.ic_add_box)
                .into(addToFavoriteBtnIv)
        }
    }

    private fun showDeleteButton() {
        isDeleteButtonVisible = !isDeleteButtonVisible
        viewBinding.deleteMovieButton.visibility = if (isDeleteButtonVisible) View.VISIBLE else View.GONE
    }
}