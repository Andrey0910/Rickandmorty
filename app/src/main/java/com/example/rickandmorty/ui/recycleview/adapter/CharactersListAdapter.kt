package com.example.rickandmorty.ui.recycleview.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ItemCharactersListBinding
import com.example.rickandmorty.ui.recycleview.core.BaseItem
import com.example.rickandmorty.ui.recycleview.core.BaseViewHolder
import com.example.rickandmorty.ui.recycleview.core.Item
import com.example.rickandmorty.ui.recycleview.model.CharactersListDataModel

class CharactersListAdapter (private val onItemClick: (CharactersListDataModel) -> Unit) :
    BaseItem <ItemCharactersListBinding, CharactersListDataModel> {

    override fun isRelativeItem(item: Item) = item is CharactersListDataModel

    override fun getLoyoutId() = R.layout.item_characters_list

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemCharactersListBinding, CharactersListDataModel> {
        val binding = ItemCharactersListBinding.inflate(layoutInflater, parent, false)
        return CharactersListViewHolder(binding, onItemClick)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<CharactersListDataModel> {
        TODO("Not yet implemented")
    }

    class CharactersListViewHolder(
        binding: ItemCharactersListBinding,
        val onItemClick: (CharactersListDataModel) -> Unit
    ) : BaseViewHolder<ItemCharactersListBinding, CharactersListDataModel>(binding){

        init {
            binding.root.setOnClickListener{
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onItemClick(item)
            }
        }

        override fun onBind(item: CharactersListDataModel) {
            super.onBind(item)

            with(binding){
                titleLogo.text = item.name
                speciesText.text = item.species
                statusText.text = item.status
                Glide.with(imageLogo.context)
                    .load(Uri.parse(item.image))
                    .into(imageLogo)
            }
        }

        override fun onBind(item: CharactersListDataModel, payloads: List<Any>) {
            super.onBind(item, payloads)

        }
    }

}