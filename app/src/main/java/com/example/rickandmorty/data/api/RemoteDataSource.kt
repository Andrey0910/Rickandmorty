package com.example.rickandmorty.data.api

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val networkService: NetworkService) {

    suspend fun getCharacters() = networkService.getCharacters()
    suspend fun getCharactersLoadMore(page: Int) = networkService.getCharactersLoadMore(page)
    suspend fun getLocations() = networkService.getLocations()
    suspend fun getEpisodes() = networkService.getEpisodes()
}