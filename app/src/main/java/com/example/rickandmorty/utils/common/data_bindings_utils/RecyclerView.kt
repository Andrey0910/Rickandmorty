package com.example.rickandmorty.utils.common.data_bindings_utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.ui.recycleview.core.BaseAdapter
import com.example.rickandmorty.ui.recycleview.core.Item

class RecyclerView {

    object RecycleViewBindingAdapter {

        @BindingAdapter("setAdapter")
        @JvmStatic
        fun setAdapter(
            recyclerView: RecyclerView,
            adapter: BaseAdapter?
        ) {
            adapter?.let {
                recyclerView.adapter = it
            }
        }

        @BindingAdapter("submitList")
        @JvmStatic
        fun submitList(recyclerView: RecyclerView, list: ArrayList<Item>?) {
            val adapter = recyclerView.adapter as BaseAdapter?
            adapter?.submitList(list?.toList())
        }
    }
}