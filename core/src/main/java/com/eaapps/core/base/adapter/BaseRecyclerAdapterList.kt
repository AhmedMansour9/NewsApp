package com.eaapps.core.base.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.util.Locale

abstract class BaseRecyclerAdapterList<V : ViewBinding, D : Any>(diffCallback: DiffUtil.ItemCallback<D>) :
    RecyclerView.Adapter<BaseRecyclerAdapterList.BaseViewHolder<V, D>>() {
    private var mDiffer: AsyncListDiffer<D>? = null

    private val mListener: AsyncListDiffer.ListListener<D?> = AsyncListDiffer.ListListener<D?> { previousList, currentList ->
        onCurrentListChanged(previousList, currentList)
    }

    init {
        mDiffer = AsyncListDiffer<D>(
            AdapterListUpdateCallback(this), AsyncDifferConfig.Builder<D>(diffCallback).build()
        )
        mDiffer!!.addListListener(mListener)
    }

    open fun onCurrentListChanged(previousList: List<D?>, currentList: List<D?>) {


    }

    protected open fun getItem(position: Int): D? {
        return mDiffer!!.currentList[position]
    }

    override fun getItemCount(): Int {
        return mDiffer!!.currentList.size
    }

    open fun getCurrentList(): List<D?> {
        return mDiffer!!.currentList
    }

    open fun submitList(list: List<D?>?) {
        mDiffer!!.submitList(list)
    }

    fun updateDataItem(position: Int, d: D) {
        val list: ArrayList<D?> = ArrayList(getCurrentList())
        if (list.isNotEmpty() && list.size > position) {
            list[position] = d
            submitList(list)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAllDataItem(position: Int, d: D) {
        val list: ArrayList<D?> = ArrayList(getCurrentList())
        if (list.isNotEmpty() && list.size > position) {
            list.set(position, d)
            submitList(list)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAllDataItem(blockOfCode: (java.util.ArrayList<D>) -> List<D>) {
        if (getCurrentList().isNotEmpty()) {
            submitList(blockOfCode(ArrayList(getCurrentList().map { it!! })))
            notifyDataSetChanged()
        }
    }

    fun getPosition(predicate: (Int, List<D?>) -> Boolean): Int {
        for (i in getCurrentList().indices) {
            if (predicate(i, getCurrentList())) {
                return i
            }
        }
        return -1
    }

    fun removeItem(position: Int) {
        val list: ArrayList<D?> = ArrayList(getCurrentList())
        if (list.isNotEmpty() && list.size > position) {
            list.removeAt(position)
            submitList(list)
        }
    }

    fun clearData() {
        submitList(arrayListOf())
    }

    fun RecyclerView.ViewHolder.resources(): Resources {
        return itemView.context.resources
    }

    fun RecyclerView.ViewHolder.requireContext(): Context {
        return itemView.context
    }

    abstract fun bindObject(obj: D?, v: V, holder: RecyclerView.ViewHolder)

    class BaseViewHolder<T : ViewBinding, D : Any>(private val binding: T) : RecyclerView.ViewHolder(binding.root) {
        private var list: List<D?>? = null
        fun onBind(position: Int, bind: (D?, T) -> Unit) {
            list?.apply {
                val d: D? = this[position]
                bind(d, binding)
            }
        }

        fun data(list: List<D?>) {
            this.list = list
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V, D>, position: Int) {
        holder.data(getCurrentList())
        holder.onBind(position) { d, v ->
            holder.itemView.setOnClickListener {

            }
            bindObject(d, v, holder)
        }
    }

}