package com.itis.android_homework.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentAddingMovieBinding
import com.itis.android_homework.db.entity.MovieEntity
import com.itis.android_homework.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class AddingMovieFragment : BaseFragment(R.layout.fragment_adding_movie){
    private val viewBinding by viewBinding(FragmentAddingMovieBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.addMovieActionBtn.setOnClickListener{
            val title: String = viewBinding.movieNameEt.text.toString()
            val description: String = viewBinding.movieDescEt.text.toString()
            val year: String = viewBinding.movieYearEt.text.toString()
            val imgUrl: String = viewBinding.movieImageUrlEt.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty() && year.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {

                    val existingMovie: MovieEntity? =
                        ServiceLocator.getDbInstance().movieDao.getMovieByTitleAndYear(title, year.toInt())

                    withContext(Dispatchers.Main) {
                        if (existingMovie == null) {

                            val movieEntity = MovieEntity(
                                movieId = UUID.randomUUID().toString(),
                                title = title,
                                year = year.toInt(),
                                description = description,
                                imgUrl = imgUrl
                            )
                            lifecycleScope.launch(Dispatchers.IO) {
                                ServiceLocator.getDbInstance().movieDao.addMovie(movieEntity)
                            }
                            viewBinding.movieNameEt.text?.clear()
                            viewBinding.movieDescEt.text?.clear()
                            viewBinding.movieYearEt.text?.clear()
                            viewBinding.movieImageUrlEt.text?.clear()
                            showToast("Movie successfully added")
                        } else {
                            showToast("Movie with this title and year is already registered")
                        }
                    }
                }
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ADDING_MOVIE_FRAGMENT_TAG = "ADDING_MOVIE_FRAGMENT_TAG"
    }
}