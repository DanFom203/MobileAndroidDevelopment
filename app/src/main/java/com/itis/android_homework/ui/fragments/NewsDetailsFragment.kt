package com.itis.android_homework.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.adapter.diffutil.NewsDetailsAdapter
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentNewsDetailsBinding
import com.itis.android_homework.utils.NewsDataRepository
import com.itis.android_homework.utils.ParamsKey

class NewsDetailsFragment(private val newsId: Int) : BaseFragment(R.layout.fragment_news_details) {
    private val viewBinding: FragmentNewsDetailsBinding by viewBinding(FragmentNewsDetailsBinding::bind)
    private var newsDetailsAdapter: NewsDetailsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsDetailsAdapter = null
    }

    private fun initRecyclerView() {
        newsDetailsAdapter = NewsDetailsAdapter(requireActivity() as BaseActivity)

        with(viewBinding) {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            newsDetailsRv.layoutManager = layoutManager
            newsDetailsRv.adapter = newsDetailsAdapter

            newsDetailsAdapter?.setItem(NewsDataRepository.getNewsList()[newsId - 1])
        }
    }

    companion object {
        const val NEWS_DETAILS_FRAGMENT_TAG = "NEWS_DETAILS_FRAGMENT_TAG"

        fun newInstance(newsId: Int) : NewsDetailsFragment {
            val fragment = NewsDetailsFragment(newsId)
            val args = Bundle()
            args.putInt(ParamsKey.NEWSFEED_POSITION_KEY, newsId)
            fragment.arguments = args
            return fragment
        }
    }
}