package com.itis.android_homework.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.R
import com.itis.android_homework.databinding.ItemCityBinding
import com.itis.android_homework.presentation.holder.WeatherHolder
import com.itis.android_homework.presentation.model.WeatherUiModel
import com.itis.android_homework.utils.ResManagerImpl
import javax.inject.Inject

class WeatherListAdapter(
    private val actionNext: (WeatherUiModel) -> Unit,
    private val resManager: ResManagerImpl,
) : ListAdapter<WeatherUiModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<WeatherUiModel>() {
        override fun areItemsTheSame(
            oldItem: WeatherUiModel,
            newItem: WeatherUiModel
        ): Boolean = (oldItem as? WeatherUiModel)?.name == (newItem as? WeatherUiModel)?.name

        override fun areContentsTheSame(
            oldItem: WeatherUiModel,
            newItem: WeatherUiModel
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return WeatherHolder(
            binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            actionNext = actionNext
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as? WeatherHolder)?.onBind(weatherUiModel = getItem(position))
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is WeatherUiModel -> R.layout.item_city
            else -> throw IllegalArgumentException(resManager.getString(R.string.unknown_error))
        }
}
