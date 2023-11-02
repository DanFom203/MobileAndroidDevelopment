package com.itis.android_homework.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.MainActivity
import com.itis.android_homework.R
import com.itis.android_homework.adapter.decorations.SimpleHorizontalMarginDecorator
import com.itis.android_homework.adapter.decorations.SimpleVerticalDecorator
import com.itis.android_homework.adapter.diffutil.NewsAdapter
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentNewsfeedBinding
import com.itis.android_homework.model.NewsDataModel
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.NewsDataRepository
import com.itis.android_homework.utils.ParamsKey
import com.itis.android_homework.utils.getValueInPx

class NewsfeedFragment : BaseFragment(R.layout.fragment_newsfeed) {
    private val viewBinding: FragmentNewsfeedBinding by viewBinding(FragmentNewsfeedBinding::bind)
    private var newsAdapter: NewsAdapter? = null
    private var newsCount: Int = ParamsKey.NEWS_COUNT_TEXT_KEY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        viewBinding.newsfeedActionBtn.setOnClickListener {
            val dialog = newsAdapter?.let { it1 ->
                SampleDialogFragment.newInstance(
                    it1
                )
            }
            dialog?.show(childFragmentManager, SampleDialogFragment.SAMPLE_DIALOG_FRAGMENT_TAG)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsAdapter = null
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(
            newsList = NewsDataRepository.getNews(newsCount).toMutableList(),
            onNewsClicked = ::onNewsClicked,
            onLikeClicked = ::onLikeClicked
        )

        with(viewBinding) {
            val layoutManager = if (newsCount > 12) {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }
            newsfeedRv.layoutManager = layoutManager
            newsfeedRv.adapter = newsAdapter

            val marginValue = 16.getValueInPx(resources.displayMetrics)
            newsfeedRv.addItemDecoration(SimpleHorizontalMarginDecorator(itemOffset = marginValue))
            newsfeedRv.addItemDecoration(SimpleVerticalDecorator(itemOffset = marginValue / 4))

        }
    }

    private fun onNewsClicked(newsDataModel: NewsDataModel) {
        (requireActivity() as? MainActivity)?.goToScreen(
            actionType = ActionType.REPLACE,
            destination = NewsDetailsFragment.newInstance(newsDataModel.newsId),
            tag = NewsDetailsFragment.NEWS_DETAILS_FRAGMENT_TAG,
        )
    }

    private fun onLikeClicked(position: Int, newsDataModel: NewsDataModel) {
        newsAdapter?.updateItem(position, newsDataModel)
    }

    companion object {
        const val NEWSFEED_FRAGMENT_TAG = "NEWSFEED_FRAGMENT_TAG"

        fun newInstance() = NewsfeedFragment()
    }

}