package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.characters.CharactersList
import com.example.rickandmorty.data.model.episodes.EpisodesList
import com.example.rickandmorty.data.model.locations.LocationsList
import com.example.rickandmorty.data.utils.BaseApiResponse
import com.example.rickandmorty.data.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getCharacters(): Flow<NetworkResult<CharactersList>> {
        return flow {
            emit(safeApiColl { remoteDataSource.getCharacters() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getLocations(): Flow<NetworkResult<LocationsList>>{
        return flow {
            emit(safeApiColl { remoteDataSource.getLocations() })
        }.flowOn(Dispatchers.IO)
    }
///dfdfdfdf
    suspend fun getEpisodes(): Flow<NetworkResult<EpisodesList>>{
        return flow {
            emit(safeApiColl { remoteDataSource.getEpisodes() })
        }.flowOn(Dispatchers.IO)
    }
}