package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.characters.CharactersList
import com.example.rickandmorty.data.model.episodes.EpisodesList
import com.example.rickandmorty.data.model.locations.LocationsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("character")
    suspend fun getCharacters(): Response<CharactersList>

    @GET("character")
    suspend fun getCharactersLoadMore(@Query("page") page: Int): Response<CharactersList>

    @GET("location")
    suspend fun getLocations(): Response<LocationsList>

    @GET("episode")
    suspend fun getEpisodes(): Response<EpisodesList>
}