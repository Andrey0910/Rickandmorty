package com.example.rickandmorty.ui.recycleview.core


import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

class BaseAdapter(
    private val fingerprints: List<BaseItem<*, *>>,
) : ListAdapter<Item, BaseViewHolder<ViewBinding, Item>>(
    BaseDiffUtil(fingerprints)
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding, Item> {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, Item>, position: Int) {
        TODO("Not yet implemented")
    }
}