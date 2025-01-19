package com.eaapps.search.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.eaapps.core.base.adapter.BaseRecyclerAdapterList
import com.eaapps.favorite.R
 import com.eaapps.search.databinding.SearchItemBinding
import com.eaapps.search.domain.entity.SearchEntity

class SearchAdapter(private val onSelectFavorite: (SearchEntity) -> Unit, private val onSelectHeadline: (String) -> Unit) :
    BaseRecyclerAdapterList<SearchItemBinding, SearchEntity>(SearchDiffCallback) {

    override fun bindObject(obj: SearchEntity?, v: SearchItemBinding, holder: RecyclerView.ViewHolder) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SearchItemBinding, SearchEntity> =
        BaseViewHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}

object SearchDiffCallback : DiffUtil.ItemCallback<SearchEntity>() {
    override fun areItemsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean = oldItem.url == newItem.url

}