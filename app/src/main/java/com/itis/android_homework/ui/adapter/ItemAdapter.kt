package com.itis.android_homework.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.databinding.ItemHighRectangleCvBinding
import com.itis.android_homework.databinding.ItemSquareCvBinding
import com.itis.android_homework.databinding.ItemWideRectangleCvBinding
import com.itis.android_homework.ui.holder.ItemHighRectangleViewHolder
import com.itis.android_homework.ui.holder.ItemSquareViewHolder
import com.itis.android_homework.ui.holder.ItemWideRectangleViewHolder

class ItemAdapter(
    private val itemList: MutableList<Int>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SQUARE -> ItemSquareViewHolder(ItemSquareCvBinding.inflate(inflater, parent, false))
            VIEW_TYPE_WIDE_RECTANGLE -> ItemWideRectangleViewHolder(ItemWideRectangleCvBinding.inflate(inflater, parent, false))
            VIEW_TYPE_HIGH_RECTANGLE -> ItemHighRectangleViewHolder(ItemHighRectangleCvBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemSquareViewHolder -> {
                // Bind data for square item
            }
            is ItemWideRectangleViewHolder -> {
                // Bind data for wide rectangle item
            }
            is ItemHighRectangleViewHolder -> {
                // Bind data for high rectangle item
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position]
    }

    override fun getItemCount(): Int = itemList.size

    companion object {
        const val VIEW_TYPE_SQUARE = 0
        const val VIEW_TYPE_HIGH_RECTANGLE = 1
        const val VIEW_TYPE_WIDE_RECTANGLE = 2
    }
}