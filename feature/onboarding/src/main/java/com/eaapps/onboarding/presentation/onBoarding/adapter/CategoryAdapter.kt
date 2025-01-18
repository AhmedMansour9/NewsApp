package com.eaapps.onboarding.presentation.onBoarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eaapps.core.ui.adapter.BaseRecyclerAdapterList
import com.eaapps.onboarding.databinding.ItemCategoryBinding

class CategoryAdapter(private val onSelectItem: (List<String>) -> Unit) : BaseRecyclerAdapterList<ItemCategoryBinding, String>(CategoryDiffCallback) {
    private val selectedCategories = mutableSetOf<String>()

    override fun bindObject(obj: String?, v: ItemCategoryBinding, holder: RecyclerView.ViewHolder) {
        with(v) {
            checkboxCategory.text = obj
            checkboxCategory.isChecked = selectedCategories.contains(obj)
            checkboxCategory.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    selectedCategories.add(obj!!)
                else
                    selectedCategories.remove(obj)
                onSelectItem(selectedCategories.toList())

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemCategoryBinding, String> =
        BaseViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}

object CategoryDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

}