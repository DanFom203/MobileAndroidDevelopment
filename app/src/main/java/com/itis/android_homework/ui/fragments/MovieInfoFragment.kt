package com.itis.android_homework.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.ItemMovieInformationBinding
import com.itis.android_homework.db.entity.RatingEntity
import com.itis.android_homework.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieInfoFragment : BaseFragment(R.layout.item_movie_information) {
    private val viewBinding by viewBinding(ItemMovieInformationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)

        val userId = sharedPreferences.getString("user_id", "")

        val title = arguments?.getString(TITLE) ?: ""
        val year = arguments?.getInt(YEAR) ?: 0


        lifecycleScope.launch {
            val movieEntity = withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().movieDao.getMovieByTitleAndYear(title, year)
            }

            movieEntity?.let { movie ->
                with(viewBinding) {
                    val movieId = movie.movieId
                    movieNameTv.text = movie.title
                    movieYearTv.text = movie.year.toString()
                    movieIdTv.text = movieId
                    movieDescTv.text = movie.description

                    Glide.with(requireContext())
                        .load(movie.imgUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(movieIv)

                    val userRating = withContext(Dispatchers.IO) {
                        ServiceLocator.getDbInstance().ratingDao.getRatingByUserAndMovieId(
                            userId!!,
                            movie.movieId
                        )
                    }

                    val averageRating = withContext(Dispatchers.IO) {
                        ServiceLocator.getDbInstance().ratingDao.getAverageRatingById(movieId)
                    }

                    filmRatingRb.rating = userRating ?: 0.0f

                    averageRatingTv.text = averageRating.toString()

                    goBackBtn.setOnClickListener {
                        findNavController().navigateUp()
                    }

                    filmRatingRb.setOnRatingBarChangeListener { _, rating, fromUser ->
                        if (fromUser) {
                            val movieRating = RatingEntity(userId!!, movie.movieId, rating)
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    ServiceLocator.getDbInstance().ratingDao.addRating(movieRating)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TITLE = "title"
        private const val YEAR = "year"

        fun createBundle(title: String ,year: Int): Bundle {
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putInt(YEAR, year)
            return bundle
        }
    }
}