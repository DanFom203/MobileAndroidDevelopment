package com.itis.android_homework.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentSecondScreenBinding
import com.itis.android_homework.ui.adapter.ItemAdapter
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.ItemsDataGenerator

class SecondScreenFragment : BaseFragment(R.layout.fragment_second_screen) {
    private var _viewBinding: FragmentSecondScreenBinding? = null
    private val viewBinding: FragmentSecondScreenBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentSecondScreenBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val numberOfElements = arguments?.getInt(ARG_NUMBER_OF_ELEMENTS_TAG, 0) ?: 0
        initGrid(numberOfElements)
    }

    private fun initGrid(numberOfElements :  Int) {
        val itemAdapter = ItemAdapter(
            itemList = ItemsDataGenerator.generateItemsList(numberOfElements)
        )


        with(viewBinding) {
            val staggeredGridLayoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            recyclerView.layoutManager = staggeredGridLayoutManager

            recyclerView.adapter = itemAdapter

            recyclerView.viewTreeObserver.addOnPreDrawListener {
                // Set full span for wide rectangle items after the first layout pass
                recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        val position = parent.getChildAdapterPosition(view)
                        val viewType = itemAdapter.getItemViewType(position)

                        if (viewType == 2) {
                            val layoutParams =
                                view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                            layoutParams.isFullSpan = true
                        }
                    }
                })
                true
            }

            secondScreenActionBtn.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        const val SECOND_SCREEN_FRAGMENT_TAG = "SECOND_SCREEN_FRAGMENT"
        const val ARG_NUMBER_OF_ELEMENTS_TAG = "ARG_NUMBER_OF_ELEMENTS"
        fun newInstance(numberOfElements: Int): SecondScreenFragment {
            val fragment = SecondScreenFragment()
            val args = Bundle()
            args.putInt(ARG_NUMBER_OF_ELEMENTS_TAG, numberOfElements)
            fragment.arguments = args
            return fragment
        }
    }
}