package com.example.rickandmorty.ui.recycleview.adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ItemCharactersListBinding
import com.example.rickandmorty.ui.recycleview.core.BaseItem
import com.example.rickandmorty.ui.recycleview.core.BaseViewHolder
import com.example.rickandmorty.ui.recycleview.core.Item
import com.example.rickandmorty.ui.recycleview.model.CharactersListDataModel
import com.example.rickandmorty.utils.Constants.STATUS_ALIVE
import com.example.rickandmorty.utils.Constants.STATUS_DEAD
import com.example.rickandmorty.utils.Preferences
import com.example.rickandmorty.utils.common.extensions.setCustomText
import com.google.android.material.imageview.ShapeableImageView
import timber.log.Timber

class CharactersListAdapter(private val onItemClick: (CharactersListDataModel) -> Unit) :
    BaseItem<ItemCharactersListBinding, CharactersListDataModel> {

    override fun isRelativeItem(item: Item) = item is CharactersListDataModel

    override fun getLoyoutId() = R.layout.item_characters_list

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemCharactersListBinding, CharactersListDataModel> {
        val binding = ItemCharactersListBinding.inflate(layoutInflater, parent, false)
        return CharactersListViewHolder(binding, onItemClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<CharactersListDataModel>() {
        override fun areItemsTheSame(
            oldItem: CharactersListDataModel,
            newItem: CharactersListDataModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CharactersListDataModel,
            newItem: CharactersListDataModel
        ) = oldItem.id == newItem.id

        override fun getChangePayload(
            oldItem: CharactersListDataModel,
            newItem: CharactersListDataModel
        ): Any? {
            when {
                oldItem.favorite != newItem.favorite -> return newItem.favorite
            }

            return super.getChangePayload(oldItem, newItem)
        }
    }

    class CharactersListViewHolder(
        binding: ItemCharactersListBinding,
        val onItemClick: (CharactersListDataModel) -> Unit
    ) : BaseViewHolder<ItemCharactersListBinding, CharactersListDataModel>(binding) {

        init {

            binding.iconFavorite.setOnClickListener {
                with(binding) {
                    if (getFavorite(item)) {
                        iconFavorite.setImageResource(R.drawable.ic_favorite_black)
                        updateShareData(item, false)
                    } else {
                        iconFavorite.setImageResource(R.drawable.ic_favorite)
                        updateShareData(item, true)
                    }
                }
            }

            binding.root.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onItemClick(item)
            }
        }

        override fun onBind(item: CharactersListDataModel) {
            super.onBind(item)

            val sharedData: ArrayList<CharactersListDataModel>? = Preferences.get("ALL_CHARACTERS_DATA")
            var itemFavotite = item.favorite

            if (!sharedData.isNullOrEmpty()) {
                sharedData.forEach {
                    if (it.id == item.id) itemFavotite = it.favorite
                }
            }


            with(binding) {
                titleLogo.text = item.name
                speciesText.setCustomText(R.string.card_title_species_text, item.species)
                statusText.setCustomText(R.string.card_title_status_text, item.status)

                loadGlide(item.image, imageLogo)

//                val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
//                val requestBuilder: RequestBuilder<Drawable> = Glide.with(binding.imageLogo.context)
//                    .asDrawable().sizeMultiplier(0.1f)
//
//                Glide.with(imageLogo.context)
//                    .load(Uri.parse(item.image))
//                    .transition(DrawableTransitionOptions.withCrossFade(factory))
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .thumbnail(requestBuilder)
//                    .apply(
//                        RequestOptions()
//                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                            .format(DecodeFormat.PREFER_RGB_565)
//                    )
//                    .into(imageLogo)

                if (itemFavotite) {
                    iconFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    iconFavorite.setImageResource(R.drawable.ic_favorite_black)
                }

                imgStatus.imageTintList =
                    ContextCompat.getColorStateList(
                        imgStatus.context,
                        when (item.status) {
                            STATUS_ALIVE -> R.color.card_status_img_green
                            STATUS_DEAD -> R.color.card_status_img_red
                            else -> R.color.card_status_img_grey
                        }
                    )
            }
        }

        override fun onBind(item: CharactersListDataModel, payloads: List<Any>) {
            super.onBind(item, payloads)

            val sharedData: ArrayList<CharactersListDataModel>? = Preferences.get("ALL_CHARACTERS_DATA")
            var itemFavotite = item.favorite

            if (!sharedData.isNullOrEmpty()) {
                sharedData.forEach {
                    if (it.id == item.id) itemFavotite = it.favorite
                }
            }

            with(binding) {
                if (itemFavotite) {
                    iconFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    iconFavorite.setImageResource(R.drawable.ic_favorite_black)
                }
            }
        }

        private fun updateShareData(item: CharactersListDataModel, value: Boolean) {

            val sharedData: ArrayList<CharactersListDataModel>? = Preferences.get("ALL_CHARACTERS_DATA")

            if (!sharedData.isNullOrEmpty()) {
                sharedData.forEachIndexed { index, id ->
                    if (id.id == item.id) {
                        sharedData[index] = item.copy(favorite = value)
                    }
                }

                Preferences.put(sharedData, "ALL_CHARACTERS_DATA")
            }
        }

        private fun getFavorite(item: CharactersListDataModel): Boolean {

            var isFavorite = false
            val sharedData: ArrayList<CharactersListDataModel>? = Preferences.get("ALL_CHARACTERS_DATA")

            if (!sharedData.isNullOrEmpty()) {
                sharedData.forEachIndexed { index, id ->
                    if (id.id == item.id) {
                        isFavorite = sharedData[index].favorite
                    }
                }
            }
            return isFavorite
        }

        private fun loadGlide(image: Any, view: ShapeableImageView) {
//            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
//            val requestBuilder: RequestBuilder<Drawable> = Glide.with(binding.imageLogo.context)
//                .asDrawable().sizeMultiplier(0.1f)
            Glide.with(binding.imageLogo.context)
                .load(
                    image
                )
//                .transition(DrawableTransitionOptions.withCrossFade(factory))
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .thumbnail(requestBuilder)
//                .apply(
//                    RequestOptions()
//                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                        .format(DecodeFormat.PREFER_RGB_565)
//                )
                .into(view)
        }
    }

}