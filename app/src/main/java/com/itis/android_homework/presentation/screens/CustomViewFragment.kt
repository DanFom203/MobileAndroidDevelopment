package com.itis.android_homework.presentation.screens

import android.os.Bundle
import android.view.View
import com.itis.android_homework.CustomGraphView
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment

class CustomViewFragment : BaseFragment(R.layout.fragment_custom_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val graphView = view.findViewById<CustomGraphView>(R.id.graphView)
        val points = listOf(
            Pair(0f, 10f),
            Pair(1f, 20f),
            Pair(2f, 15f),
            Pair(3f, 30f),
            Pair(4f, 25f)
        )

        graphView.setData(points)
    }
}