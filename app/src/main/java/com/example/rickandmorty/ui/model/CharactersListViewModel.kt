package com.example.rickandmorty.ui.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.Application
import com.example.rickandmorty.data.api.Repository
import com.example.rickandmorty.data.model.characters.CharactersList
import com.example.rickandmorty.data.utils.NetworkResult
import com.example.rickandmorty.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val response: MutableLiveData<NetworkResult<CharactersList>> = MutableLiveData()

    private val stateSuccess: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()
    val stateSuccessView: LiveData<SingleEvent<Boolean>> = stateSuccess

    private val stateLoading: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()
    val stateLoadingView: LiveData<SingleEvent<Boolean>> = stateLoading


}