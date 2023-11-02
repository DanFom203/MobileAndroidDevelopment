package com.itis.android_homework.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentMainPageBinding
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.ParamsKey

class MainPageFragment : BaseFragment(R.layout.fragment_main_page) {
    private var _viewBinding: FragmentMainPageBinding? = null
    private val viewBinding: FragmentMainPageBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentMainPageBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(viewBinding) {
            mainPageActionBtn.setOnClickListener {
                val newsCount : Int
                if (countOfNewsEt.text.toString().isEmpty() || countOfNewsEt.text.toString().toInt() > 45) {
                    countOfNewsEt.error = getString(R.string.invalid_count_1)
                    return@setOnClickListener
                }
                if (countOfNewsEt.text.toString().toInt() == 0) {
                    (requireActivity() as? BaseActivity)?.goToScreen(
                        actionType = ActionType.REPLACE,
                        destination = ZeroNewsFragment.newInstance(),
                        tag = ZeroNewsFragment.ZERO_NEWS_PAGE_FRAGMENT_TAG,
                        isAddToBackStack = true,
                    )
                } else {
                    ParamsKey.NEWS_COUNT_TEXT_KEY = countOfNewsEt.text.toString().toInt()
                    (requireActivity() as? BaseActivity)?.goToScreen(
                        actionType = ActionType.REPLACE,
                        destination = NewsfeedFragment.newInstance(),
                        tag = NewsfeedFragment.NEWSFEED_FRAGMENT_TAG,
                        isAddToBackStack = true,
                    )
                }
            }
        }
    }

    companion object {
        const val MAIN_PAGE_FRAGMENT_TAG = "MAIN_PAGE_FRAGMENT_TAG"

        fun newInstance() = MainPageFragment()
    }
}