package com.example.rickandmorty.ui.fragment

data class CharacterItemDataModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val name_location: String
)