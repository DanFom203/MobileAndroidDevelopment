package com.itis.android_homework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentSecondPageBinding
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.ParamsKey

class SecondPageFragment : BaseFragment(R.layout.fragment_second_page) {
    private var _viewBinding: FragmentSecondPageBinding? = null
    private val viewBinding: FragmentSecondPageBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = FragmentSecondPageBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(viewBinding) {
            arguments?.getString(ParamsKey.MESSAGE_TEXT_KEY)?.let { message ->
                headerS2TextTv.text =
                    if (message.isNotEmpty()) message else getString(R.string.second_screen)
            }
            secondPageActionBtn1.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()

                (requireActivity() as? BaseActivity)?.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = ThirdPageFragment.newInstance(ParamsKey.MESSAGE_TEXT_KEY),
                    tag = ThirdPageFragment.THIRD_PAGE_FRAGMENT_TAG,
                    isAddToBackStack = true,
                )
            }
            secondPageActionBtn2.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }


    companion object {
        const val SECOND_PAGE_FRAGMENT_TAG = "SECOND_PAGE_FRAGMENT_TAG"

        fun newInstance(message: String) :SecondPageFragment = SecondPageFragment().apply {
            arguments = bundleOf(ParamsKey.MESSAGE_TEXT_KEY to message)
        }
    }
}