package com.eaapps.headlines.presentation.headline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.eaapps.core.base.adapter.BaseRecyclerAdapterList
import com.eaapps.favorite.R
import com.eaapps.headlines.databinding.TopHeadlineItemBinding
import com.eaapps.headlines.domain.entity.HeadlineEntity

class TopHeadlineAdapter(private val onSelectFavorite: (HeadlineEntity) -> Unit, private val onSelectHeadline: (String) -> Unit) :
    BaseRecyclerAdapterList<TopHeadlineItemBinding, HeadlineEntity>(HeadlineDiffCallback) {

    override fun bindObject(obj: HeadlineEntity?, v: TopHeadlineItemBinding, holder: RecyclerView.ViewHolder) {
        with(v) {
            image.load(obj?.image)
            title.text = obj?.title ?: ""
            source.text = obj?.source ?: ""
            date.text = obj?.date ?: ""
            desc.text = obj?.shortDescription ?: ""

            if (obj?.favorite == true)
                favBtn.setImageResource(R.drawable.baseline_favorite_32)
            else
                favBtn.setImageResource(R.drawable.baseline_favorite_border_32)

            favBtn.isEnabled = obj?.favorite == false

            root.setOnClickListener {
                obj?.url?.let { it1 -> onSelectHeadline(it1) }
            }
            favBtn.setOnClickListener {
                obj?.apply {
                    onSelectFavorite(this)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TopHeadlineItemBinding, HeadlineEntity> =
        BaseViewHolder(TopHeadlineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}

object HeadlineDiffCallback : DiffUtil.ItemCallback<HeadlineEntity>() {
    override fun areItemsTheSame(oldItem: HeadlineEntity, newItem: HeadlineEntity): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: HeadlineEntity, newItem: HeadlineEntity): Boolean = oldItem.url == newItem.url

}