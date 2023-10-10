package com.itis.android_homework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentThirdPageBinding
import com.itis.android_homework.utils.ParamsKey

class ThirdPageFragment : BaseFragment (R.layout.fragment_third_page){
    private var _viewBinding: FragmentThirdPageBinding? = null
    private val viewBinding: FragmentThirdPageBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = FragmentThirdPageBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        arguments?.getString(ParamsKey.MESSAGE_TEXT_KEY)?.let { message ->
            viewBinding.headerS3TextTv.text =
                if (message.isNotEmpty()) message else getString(R.string.third_screen)
        }
    }
    companion object {
        const val THIRD_PAGE_FRAGMENT_TAG = "THIRD_PAGE_FRAGMENT_TAG"

        fun newInstance(message: String) :ThirdPageFragment = ThirdPageFragment().apply {
            arguments = bundleOf(ParamsKey.MESSAGE_TEXT_KEY to message)
        }
    }
}