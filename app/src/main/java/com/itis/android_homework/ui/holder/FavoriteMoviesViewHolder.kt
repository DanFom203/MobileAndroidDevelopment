package com.itis.android_homework.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.android_homework.R
import com.itis.android_homework.databinding.ItemMovieHorizontalBinding
import com.itis.android_homework.model.MovieModel

class FavoriteMoviesViewHolder(
    private val viewBinding: ItemMovieHorizontalBinding,
    private val glide: RequestManager,
    private val onMovieClicked: ((MovieModel) -> Unit),
    private val onDeleteFromFavoritesClicked: ((MovieModel) -> Unit),
) : RecyclerView.ViewHolder(viewBinding.root) {
    private var item: MovieModel? = null
    private val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    init {
        viewBinding.root.setOnClickListener {
            this.item?.let(onMovieClicked)
        }
        viewBinding.deleteFromFavoritesBtnIv.setOnClickListener {
            this.item?.let {
                onDeleteFromFavoritesClicked(it)
            }
        }

    }

    fun bindItem(item: MovieModel) {
        this.item = item
        with(viewBinding) {
            movieTitleTv.text = item.title

            glide.load(item.imgUrl)
                .apply(options)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(movieIv)
            glide.load(R.drawable.ic_delete_favorite)
                .into(deleteFromFavoritesBtnIv)
        }
    }

}