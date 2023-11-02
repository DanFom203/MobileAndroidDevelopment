package com.itis.android_homework.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itis.android_homework.R
import com.itis.android_homework.adapter.diffutil.NewsAdapter
import com.itis.android_homework.databinding.FragmentSampleDialogBinding
import com.itis.android_homework.utils.NewsDataRepository
import com.itis.android_homework.utils.ParamsKey


class SampleDialogFragment(private var newsAdapter: NewsAdapter?) : BottomSheetDialogFragment(R.layout.fragment_sample_dialog) {

    private val viewBinding: FragmentSampleDialogBinding by viewBinding(FragmentSampleDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculateViewDialogHeight()
        with(viewBinding) {
            dialogActionBtn.setOnClickListener {
                if (addNewsEt.text.toString().isEmpty() || addNewsEt.text.toString().toInt() > 5) {
                    addNewsEt.error = getString(R.string.invalid_count_2)
                    return@setOnClickListener
                }
                if (addNewsEt.text.toString().toInt() == 0) {
                    onDestroyView()
                } else {
                    val addCount = addNewsEt.text.toString().toInt()
                    newsAdapter?.setItems(NewsDataRepository.getNews(addCount))

                    ParamsKey.NEWS_COUNT_TEXT_KEY += addCount
                    onDestroyView()
                }
            }

        }
    }

    private fun calculateViewDialogHeight() {
        val displayMetrics = requireContext().resources.displayMetrics
        val dialogHeight = displayMetrics.heightPixels / 3

        val layoutParams =
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .apply {
                    height = dialogHeight
                }

        this.viewBinding.root.layoutParams = layoutParams
    }

    companion object {
        const val SAMPLE_DIALOG_FRAGMENT_TAG = "SAMPLE_DIALOG_FRAGMENT_TAG"

        fun newInstance(newsAdapter: NewsAdapter) = SampleDialogFragment(newsAdapter)
    }
}