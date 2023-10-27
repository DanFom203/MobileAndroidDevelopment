package com.itis.android_homework.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.itis.android_homework.fragments.QuestionnaireFragment
import com.itis.android_homework.utils.ParamsKey

class ViewPagerAdapter (manager: FragmentManager,
                        lifecycle: Lifecycle,
                        private val itemCount: Int,
                        private val questionCount: Int
    ) : FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount(): Int {
        return if (itemCount <= 0) 0 else Integer.MAX_VALUE
    }

    override fun createFragment(position: Int): Fragment {
        val realPosition = position % itemCount
        return QuestionnaireFragment.newInstance(realPosition, questionCount)
    }
}