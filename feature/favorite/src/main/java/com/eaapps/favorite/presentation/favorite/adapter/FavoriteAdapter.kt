package com.eaapps.favorite.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.eaapps.core.base.adapter.BaseRecyclerAdapterList
import com.eaapps.favorite.R
import com.eaapps.favorite.databinding.FavoriteItemBinding
import com.eaapps.favorite.domain.entity.FavoriteEntity

class FavoriteAdapter(private val onRemoveFavorite: (FavoriteEntity) -> Unit, private val onSelectHeadline: (String) -> Unit) :
    BaseRecyclerAdapterList<FavoriteItemBinding, FavoriteEntity>(FavoriteDiffCallback) {

    override fun bindObject(obj: FavoriteEntity?, v: FavoriteItemBinding, holder: RecyclerView.ViewHolder) {
        with(v) {
            image.load(obj?.image)
            title.text = obj?.title ?: ""
            source.text = obj?.source ?: ""
            date.text = obj?.date ?: ""

            root.setOnClickListener {
                obj?.url?.let { it1 -> onSelectHeadline(it1) }
            }
            favBtn.setOnClickListener {
                obj?.let { it1 -> onRemoveFavorite(it1) }
             }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FavoriteItemBinding, FavoriteEntity> =
        BaseViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}

object FavoriteDiffCallback : DiffUtil.ItemCallback<FavoriteEntity>() {
    override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean = oldItem.url == newItem.url

}