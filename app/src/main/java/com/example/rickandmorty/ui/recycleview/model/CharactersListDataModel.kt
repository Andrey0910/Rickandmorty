package com.example.rickandmorty.ui.recycleview.model

import com.example.rickandmorty.ui.recycleview.core.Item

data class CharactersListDataModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val favorite: Boolean,
    val name_location: String
) : Item