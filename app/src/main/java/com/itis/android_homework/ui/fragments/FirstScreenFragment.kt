package com.itis.android_homework.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentFirstScreenBinding
import com.itis.android_homework.utils.ActionType

class FirstScreenFragment: BaseFragment(R.layout.fragment_first_screen) {
    private var _viewBinding: FragmentFirstScreenBinding? = null
    private val viewBinding: FragmentFirstScreenBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentFirstScreenBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(viewBinding) {
            firstScreenActionBtn.setOnClickListener {
                val numberOfElements = countEt.text.toString().toIntOrNull()
                if (numberOfElements == null) {
                    countEt.error = getString(R.string.invalid_count)
                }
                if (numberOfElements != null && numberOfElements > 0) {
                    navigateToSecondScreen(numberOfElements)
                } else {
                    showToast("Please enter a valid number of elements")
                }
            }
        }
    }

    private fun navigateToSecondScreen(numberOfElements: Int) {
        (requireActivity() as? BaseActivity)?.goToScreen(
            actionType = ActionType.REPLACE,
            destination = SecondScreenFragment.newInstance(numberOfElements),
            tag = SecondScreenFragment.SECOND_SCREEN_FRAGMENT_TAG,
            isAddToBackStack = true
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
    companion object {
        const val FIRST_SCREEN_FRAGMENT_TAG = "FIRST_SCREEN_FRAGMENT"
        fun newInstance() = FirstScreenFragment()
    }
}