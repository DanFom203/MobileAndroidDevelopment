package com.itis.android_homework.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.android_homework.databinding.ItemMovieHorizontalBinding
import com.itis.android_homework.model.MovieModel
import com.itis.android_homework.ui.holder.FavoriteMoviesViewHolder
import com.itis.android_homework.ui.holder.MoviesViewHolder

class FavoriteMoviesAdapter (
    private val glide: RequestManager,
    private val onMovieClicked: ((MovieModel) -> Unit),
    private val onDeleteFromFavoritesClicked: ((MovieModel) -> Unit),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var favoriteFilmsList = mutableListOf<MovieModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteMoviesViewHolder(
            viewBinding = ItemMovieHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onMovieClicked = onMovieClicked,
            onDeleteFromFavoritesClicked = onDeleteFromFavoritesClicked,
            glide = glide
        )
    }

    override fun getItemCount(): Int = favoriteFilmsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? FavoriteMoviesViewHolder)?.bindItem(item = favoriteFilmsList[position])

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<MovieModel>) {
        favoriteFilmsList.clear()
        favoriteFilmsList.addAll(list)
        notifyDataSetChanged()
    }

}