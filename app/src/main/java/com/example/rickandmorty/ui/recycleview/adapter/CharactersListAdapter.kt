package com.example.rickandmorty.ui.recycleview.adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
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
import com.example.rickandmorty.utils.common.extensions.getCustomText
import com.example.rickandmorty.utils.common.extensions.setCustomText
import com.google.android.material.imageview.ShapeableImageView
import timber.log.Timber

class CharactersListAdapter(
    private val onItemClick: (CharactersListDataModel) -> Unit,
    private val onFavoriteClick: (CharactersListDataModel) -> Unit
) :
    BaseItem<ItemCharactersListBinding, CharactersListDataModel> {

    override fun isRelativeItem(item: Item) = item is CharactersListDataModel

    override fun getLoyoutId() = R.layout.item_characters_list

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemCharactersListBinding, CharactersListDataModel> {
        val binding = ItemCharactersListBinding.inflate(layoutInflater, parent, false)
        return CharactersListViewHolder(binding, onItemClick, onFavoriteClick)
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
        ) = oldItem == newItem

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
        val onItemClick: (CharactersListDataModel) -> Unit,
        val onFavoriteClick: (CharactersListDataModel) -> Unit
    ) : BaseViewHolder<ItemCharactersListBinding, CharactersListDataModel>(binding) {

        init {
            binding.clElementCard.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onItemClick(item)
            }

            binding.iconFavorite.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                onFavoriteClick(item)
            }
        }

        override fun onBind(item: CharactersListDataModel) {
            super.onBind(item)

            with(binding) {
                titleLogo.futureText(item.name)
                speciesText.getCustomText(R.string.card_title_species_text, item.species).also {
                    speciesText.futureText(it)
                }
                statusText.getCustomText(R.string.card_title_status_text, item.status).also {
                    statusText.futureText(it)
                }

                loadGlide(item.image, imageLogo)

                if (item.favorite) {
                    Timber.tag("RRR").i("onBind1===id==%s====favirite=====%s",item.id, item.favorite)
                    iconFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    Timber.tag("RRR").i("onBind2===id==%s====favirite=====%s",item.id, item.favorite)
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

            with(binding) {
                if (item.favorite) {
                    iconFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    iconFavorite.setImageResource(R.drawable.ic_favorite_black)
                }
            }
        }

        private fun loadGlide(image: Any, view: ShapeableImageView) {
            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
            val requestBuilder: RequestBuilder<Drawable> = Glide.with(binding.imageLogo.context)
                .asDrawable().sizeMultiplier(0.1f)
            Glide.with(binding.imageLogo.context)
                .load(
                    image
                )
                .placeholder(R.drawable.ic_not_internet)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(requestBuilder)
                .apply(
                    RequestOptions()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .format(DecodeFormat.PREFER_RGB_565)
                )
                .into(view)
        }
    }

}

// ускоряет загрузку текста в textview
private fun AppCompatTextView.futureText(text: String) {
    setTextFuture(
        PrecomputedTextCompat.getTextFuture(
            text,
            TextViewCompat.getTextMetricsParams(this),
            /*optional custom executor*/ null
        )
    )
}