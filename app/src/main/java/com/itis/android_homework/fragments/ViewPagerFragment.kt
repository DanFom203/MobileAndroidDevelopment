package com.itis.android_homework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.itis.android_homework.R
import com.itis.android_homework.adapter.ViewPagerAdapter
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentViewPagerBinding

class ViewPagerFragment : BaseFragment(R.layout.fragment_view_pager) {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.fragmentVp
        val inputNumber = arguments?.getInt(INPUT_NUMBER_KEY) ?: 0
        viewPagerAdapter =
            ViewPagerAdapter(childFragmentManager, lifecycle, inputNumber, inputNumber)
        viewPager.adapter = viewPagerAdapter

        val middlePosition = Integer.MAX_VALUE / 2
        val remainder = middlePosition % inputNumber
        val initialPosition = middlePosition - remainder
        viewPager.setCurrentItem(initialPosition, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val INPUT_NUMBER_KEY = "input_number"
        const val QUESTIONNAIRE_PAGE_FRAGMENT_TAG = "QUESTIONNAIRE_PAGE_FRAGMENT_TAG"

        fun newInstance(inputNumber: Int): ViewPagerFragment {
            val fragment = ViewPagerFragment()
            val args = Bundle()
            args.putInt(INPUT_NUMBER_KEY, inputNumber)
            fragment.arguments = args
            return fragment
        }
    }
}