package com.itis.android_homework.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentMainScreenBinding
import com.itis.android_homework.db.entity.UsersFavouriteMoviesEntity
import com.itis.android_homework.di.ServiceLocator
import com.itis.android_homework.model.MovieModel
import com.itis.android_homework.ui.adapter.FavoriteMoviesAdapter
import com.itis.android_homework.ui.adapter.MoviesAdapter
import com.itis.android_homework.ui.mappers.MovieMapper
import com.itis.android_homework.utils.ParamsKey
import com.itis.android_homework.utils.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenFragment : BaseFragment(R.layout.fragment_main_screen) {

    private val viewBinding by viewBinding(FragmentMainScreenBinding::bind)
    private var allMoviesAdapter: MoviesAdapter? = null
    private var favoriteMoviesAdapter: FavoriteMoviesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initFloatingBtn()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        allMoviesAdapter = null
        favoriteMoviesAdapter = null
    }

    private fun initFloatingBtn() {
        viewBinding.sortFab.setOnClickListener{
            val options = arrayOf("Год выпуска по убыванию", "Год выпуска по возрастанию", "Рейтинг пользователя по убыванию", "Рейтинг пользователя по возрастанию")

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Выберите сортировку")
                .setItems(options) { _, which ->
                    handleSortOptionSelected(which)
                }

            builder.create().show()
        }
    }

    private fun handleSortOptionSelected(selectedIndex: Int) {
        when (selectedIndex) {
            0 -> ParamsKey.MOVIE_SORTING_KEY = SortType.YEAR_DESC
            1 -> ParamsKey.MOVIE_SORTING_KEY = SortType.YEAR_ASC
            2 -> ParamsKey.MOVIE_SORTING_KEY = SortType.RATING_DESC
            3 -> ParamsKey.MOVIE_SORTING_KEY = SortType.RATING_ASC
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        allMoviesAdapter = MoviesAdapter(
            onMovieClicked = ::onMovieClicked,
            onLikeClicked = ::onLikeClicked,
            onDeleteClicked = ::onDeleteClicked,
            glide = Glide.with(requireContext())
        )

        favoriteMoviesAdapter = FavoriteMoviesAdapter(
            onMovieClicked = ::onMovieClicked,
            onDeleteFromFavoritesClicked = ::onDeleteFromFavoritesClicked,
            glide = Glide.with(requireContext())
        )

        with(viewBinding) {
            val allMoviesLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            allMoviesRv.layoutManager = allMoviesLayoutManager
            allMoviesRv.adapter = allMoviesAdapter

            val favoriteMoviesLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            favoriteMoviesRv.layoutManager = favoriteMoviesLayoutManager
            favoriteMoviesRv.adapter = favoriteMoviesAdapter

            val sharedPreferences = requireContext().getSharedPreferences(
                "user_session",
                Context.MODE_PRIVATE
            )
            val userId = sharedPreferences.getString("user_id", "")

            lifecycleScope.launch(Dispatchers.IO) {
                var allMoviesModelList: List<MovieModel> = ArrayList()
                when (ParamsKey.MOVIE_SORTING_KEY) {
                    SortType.YEAR_DESC -> {
                        val allMoviesList =
                            ServiceLocator.getDbInstance().movieDao.getAllMoviesOrderByDesc()
                        allMoviesModelList = MovieMapper.toModelList(allMoviesList)
                    }
                    SortType.YEAR_ASC -> {
                        val allMoviesList =
                            ServiceLocator.getDbInstance().movieDao.getAllMoviesOrderByAsc()
                        allMoviesModelList = MovieMapper.toModelList(allMoviesList)
                    }
                    SortType.RATING_DESC -> {
                        val allMoviesList =
                            ServiceLocator.getDbInstance().movieDao.getAllMoviesOrderByRatingDesc()
                        allMoviesModelList = MovieMapper.toModelList(allMoviesList)
                    }
                    SortType.RATING_ASC -> {
                        val allMoviesList =
                            ServiceLocator.getDbInstance().movieDao.getAllMoviesOrderByRatingAsc()
                        allMoviesModelList = MovieMapper.toModelList(allMoviesList)
                    }
                }

                val favoriteMoviesIdsList = ServiceLocator.getDbInstance().usersFavouriteMoviesDao.getFavoriteFilmIdsForUser(userId!!)
                val favoriteMoviesList = ServiceLocator.getDbInstance().movieDao.getMoviesByIds(favoriteMoviesIdsList)
                val favoriteMoviesModelList: List<MovieModel> = MovieMapper.toModelList(favoriteMoviesList)

                allMoviesAdapter?.setItems(allMoviesModelList)
                favoriteMoviesAdapter?.setItems(favoriteMoviesModelList)
            }
        }
    }

    private fun onDeleteFromFavoritesClicked(movieModel: MovieModel) {
        lifecycleScope.launch(Dispatchers.IO) {
            ServiceLocator.getDbInstance().usersFavouriteMoviesDao.removeFromFavorites(movieModel.movieId)
        }
        initRecyclerView()
    }

    private fun onMovieClicked(movieModel: MovieModel) {
        findNavController().navigate(
            R.id.action_mainScreenFragment_to_movieInfoFragment,
            MovieInfoFragment.createBundle(movieModel.title, movieModel.year)
        )
    }
    private fun onLikeClicked(movieModel: MovieModel) {
        val sharedPreferences = requireContext().getSharedPreferences(
            "user_session",
            Context.MODE_PRIVATE
        )
        val userId = sharedPreferences.getString("user_id", "")
        val usersFavouriteMoviesEntity = userId?.let { UsersFavouriteMoviesEntity(it, movieModel.movieId) }

        lifecycleScope.launch(Dispatchers.IO) {
            if (usersFavouriteMoviesEntity != null) {
                ServiceLocator.getDbInstance().usersFavouriteMoviesDao.addToFavorites(usersFavouriteMoviesEntity)
            }
        }
        initRecyclerView()
    }

    private fun onDeleteClicked(movieModel: MovieModel) {
        lifecycleScope.launch(Dispatchers.IO) {
            ServiceLocator.getDbInstance().movieDao.deleteMovie(movieModel.movieId)
        }
        initRecyclerView()
    }

}