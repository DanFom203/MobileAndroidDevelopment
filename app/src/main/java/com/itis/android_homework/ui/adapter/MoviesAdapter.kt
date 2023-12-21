package com.itis.android_homework.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.android_homework.databinding.ItemMovieVerticalBinding
import com.itis.android_homework.model.MovieModel
import com.itis.android_homework.ui.holder.MoviesViewHolder

class MoviesAdapter (
    private val glide: RequestManager,
    private val onMovieClicked: ((MovieModel) -> Unit),
    private val onLikeClicked: ((MovieModel) -> Unit),
    private val onDeleteClicked: ((MovieModel) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var allFilmsList = mutableListOf<MovieModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MoviesViewHolder(
            viewBinding = ItemMovieVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onMovieClicked = onMovieClicked,
            onLikeClicked = onLikeClicked,
            onDeleteClicked = onDeleteClicked,
            glide = glide
        )
    }

    override fun getItemCount(): Int = allFilmsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MoviesViewHolder)?.bindItem(item = allFilmsList[position])

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<MovieModel>) {
        allFilmsList.clear()
        allFilmsList.addAll(list)
        notifyDataSetChanged()
    }

}