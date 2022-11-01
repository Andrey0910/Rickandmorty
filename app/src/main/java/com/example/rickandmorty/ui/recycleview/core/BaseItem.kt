package com.example.rickandmorty.ui.recycleview.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

interface BaseItem<V : ViewBinding, I : Item> {

    fun isRelativeItem(item: Item): Boolean

    @LayoutRes
    fun getLoyoutId(): Int

    fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) : BaseViewHolder<V, I>

    fun getDiffUtil(): DiffUtil.ItemCallback<I>
}