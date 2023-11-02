package com.itis.android_homework.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.databinding.ItemNewsDetailsBinding
import com.itis.android_homework.model.NewsDataModel
import com.itis.android_homework.ui.fragments.NewsfeedFragment
import com.itis.android_homework.utils.ActionType

class NewsDetailsViewHolder(
    private val viewBinding: ItemNewsDetailsBinding,
    private val activity : BaseActivity
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var item: NewsDataModel? = null

    fun bindItem(item: NewsDataModel) {
        this.item = item
        with(viewBinding) {
            newsTitleTv.text = item.newsTitle
            item.newsDetails?.let { newsDetailsTv.text = it }
            item.newsImage?.let { res ->
                newsImageIv.setImageResource(res)
            }
            newsDetailsPageActionBtn.setOnClickListener {
                activity.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = NewsfeedFragment.newInstance(),
                    tag = NewsfeedFragment.NEWSFEED_FRAGMENT_TAG,
                    isAddToBackStack = true,
                )
            }
        }
    }
}