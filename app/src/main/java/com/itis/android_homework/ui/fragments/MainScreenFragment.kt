package com.itis.android_homework.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentMainScreenBinding
import com.itis.android_homework.di.ServiceLocator
import com.itis.android_homework.model.MovieModel
import com.itis.android_homework.ui.adapter.MoviesAdapter
import com.itis.android_homework.ui.mappers.MovieMapper
import com.itis.android_homework.utils.ParamsKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenFragment : BaseFragment(R.layout.fragment_main_screen) {

    private val viewBinding by viewBinding(FragmentMainScreenBinding::bind)
    private var moviesAdapter: MoviesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        moviesAdapter = null
    }

    private fun initRecyclerView() {
        moviesAdapter = MoviesAdapter(
            onMovieClicked = ::onNewsClicked,
            onLikeClicked = ::onLikeClicked
        )

        with(viewBinding) {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            allMoviesRv.layoutManager = layoutManager
            allMoviesRv.adapter = moviesAdapter

            lifecycleScope.launch(Dispatchers.IO) {
                val list = ServiceLocator.getDbInstance().movieDao.getAllMovies()
                val newList : List<MovieModel> = MovieMapper.toModelList(list)
                moviesAdapter?.setItems(newList)
            }
        }
    }

    private fun onNewsClicked(movieModel: MovieModel) {
        parentFragmentManager.setFragmentResult(ParamsKey.DIALOG_RESULT_KEY, bundleOf("first" to "second"))
    }

    private fun onLikeClicked(position: Int, movieModel: MovieModel) {
        moviesAdapter?.updateItem(position, movieModel)
    }

    companion object {
        const val MAIN_SCREEN_FRAGMENT_TAG = "MAIN_SCREEN_FRAGMENT_TAG"
    }
}