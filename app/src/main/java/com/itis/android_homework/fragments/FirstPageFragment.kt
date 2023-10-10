package com.itis.android_homework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentFirstPageBinding
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.ParamsKey

class FirstPageFragment : BaseFragment(R.layout.fragment_first_page) {
    private var _viewBinding: FragmentFirstPageBinding? = null
    private val viewBinding: FragmentFirstPageBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = FragmentFirstPageBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(viewBinding) {

            firstPageActionBtn.setOnClickListener {
                if (messageEt.text.toString().isEmpty()) {
                    messageEt.error = getString(R.string.invalid_message)
                    return@setOnClickListener
                } else { ParamsKey.MESSAGE_TEXT_KEY = messageEt.text.toString() }
                (requireActivity() as? BaseActivity)?.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = SecondPageFragment.newInstance(ParamsKey.MESSAGE_TEXT_KEY),
                    tag = SecondPageFragment.SECOND_PAGE_FRAGMENT_TAG,
                    isAddToBackStack = true,
                )
                (requireActivity() as? BaseActivity)?.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = ThirdPageFragment.newInstance(ParamsKey.MESSAGE_TEXT_KEY),
                    tag = ThirdPageFragment.THIRD_PAGE_FRAGMENT_TAG,
                    isAddToBackStack = true,
                )
            }
        }
    }


    companion object {
        const val FIRST_PAGE_FRAGMENT_TAG = "FIRST_PAGE_FRAGMENT_TAG"

        fun newInstance() = FirstPageFragment()
    }
}