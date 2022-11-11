package com.example.rickandmorty.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterBinding
import com.example.rickandmorty.ui.model.app_view_model.NavigationViewModel
import com.example.rickandmorty.utils.BackButtonListener
import com.example.rickandmorty.utils.Constants
import com.example.rickandmorty.utils.common.extensions.setCustomText
import com.example.rickandmorty.utils.nullOnDestroy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment(), BackButtonListener  {

    private var binding: FragmentCharacterBinding by nullOnDestroy()

    private val navigationViewModel: NavigationViewModel by viewModels<NavigationViewModel>()

    private lateinit var newItem: CharacterItemDataModel

    companion object{
        fun newInstans(newItem: CharacterItemDataModel): CharacterFragment {
            return CharacterFragment().apply {
                this.newItem = newItem
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            titleCharacter.text = newItem.name
            statusText.setCustomText(R.string.character_title_status_text, newItem.status)

            imgStatus.imageTintList =
                ContextCompat.getColorStateList(
                    imgStatus.context,
                    when (newItem.status) {
                        Constants.STATUS_ALIVE -> R.color.card_status_img_green
                        Constants.STATUS_DEAD -> R.color.card_status_img_red
                        else -> R.color.card_status_img_grey
                    }
                )

            Glide.with(imageLogo.context)
                .load(Uri.parse(newItem.image))
                .into(imageLogo)

            textName.text = newItem.name
            textSpecies.text = newItem.species

            if (newItem.type.isEmpty()) {
                titleType.visibility = View.GONE
                textType.visibility = View.GONE
                ivDividerType.visibility = View.GONE
            } else textType.text = newItem.type

            textGender.text = newItem.gender
            textLocation.text = newItem.name_location
        }
    }

    override fun onBackPressed(): Boolean {
        navigationViewModel.onBackCommandClick()
        return true
    }
}