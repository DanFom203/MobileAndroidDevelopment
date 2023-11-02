package com.itis.android_homework.ui.fragments

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentZeroNewsBinding
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.ParamsKey

class ZeroNewsFragment : BaseFragment(R.layout.fragment_zero_news) {

    private val viewBinding: FragmentZeroNewsBinding by viewBinding(FragmentZeroNewsBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            zeroNewsPageActionBtn.setOnClickListener {
                (requireActivity() as? BaseActivity)?.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = MainPageFragment.newInstance(),
                    tag = MainPageFragment.MAIN_PAGE_FRAGMENT_TAG,
                    isAddToBackStack = true,
                )
            }
        }

    }

    companion object {
        const val ZERO_NEWS_PAGE_FRAGMENT_TAG = "ZERO_NEWS_PAGE_FRAGMENT_TAG"

        fun newInstance() = ZeroNewsFragment()
    }
}