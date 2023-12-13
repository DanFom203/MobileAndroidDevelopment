package com.itis.android_homework.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.databinding.ItemMovieVerticalBinding
import com.itis.android_homework.model.MovieModel
import com.itis.android_homework.ui.adapter.diffutil.MoviesDiffUtil
import com.itis.android_homework.ui.holder.MoviesViewHolder

class MoviesAdapter (
    private val onMovieClicked: ((MovieModel) -> Unit),
    private val onLikeClicked: ((Int, MovieModel) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var allFilmsList = mutableListOf<MovieModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MoviesViewHolder(
            viewBinding = ItemMovieVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onMovieClicked = onMovieClicked,
            onLikeClicked = onLikeClicked,
        )
    }

    override fun getItemCount(): Int = allFilmsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MoviesViewHolder)?.bindItem(item = allFilmsList[position])

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            (payloads.first() as? Boolean)?.let {
                (holder as? MoviesViewHolder)?.changeLikeBtnStatus(it)
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    fun updateItem(position: Int, item: MovieModel) {
        this.allFilmsList[position] = item
        notifyItemChanged(position, item.isLiked)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<MovieModel>) {
        val diff = MoviesDiffUtil(oldItemsList = allFilmsList, newItemsList = list)
        val diffResult = DiffUtil.calculateDiff(diff)
        allFilmsList.clear()
        allFilmsList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

}