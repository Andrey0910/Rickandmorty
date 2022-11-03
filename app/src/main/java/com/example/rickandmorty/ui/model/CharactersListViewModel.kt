package com.example.rickandmorty.ui.model

import androidx.lifecycle.*
import com.example.rickandmorty.Application
import com.example.rickandmorty.data.api.Repository
import com.example.rickandmorty.data.model.characters.CharactersList
import com.example.rickandmorty.data.utils.NetworkResult
import com.example.rickandmorty.ui.recycleview.core.Item
import com.example.rickandmorty.ui.recycleview.model.CharactersListDataModel
import com.example.rickandmorty.utils.Preferences
import com.example.rickandmorty.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
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

    private val stateError: MutableLiveData<String> = MutableLiveData()
    val stateErrorView: LiveData<String> = stateError

    private val adapterData: MutableLiveData<ArrayList<Item>> = MutableLiveData()
    val adapterDataView: LiveData<ArrayList<Item>> = adapterData

    private val data = arrayListOf<Item>()
    private var sharedData: ArrayList<CharactersListDataModel> = Preferences.get("ALL_CHARACTERS_DATA")

    init {
        if (!sharedData.isNullOrEmpty()){
            charactersList()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                stateLoading.value = SingleEvent(true)
                delay(500)
                charactersList()
            }
        }
    }

    private fun charactersList() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                repository.getCharacters().collect {
                    values -> response.value = values
                }

                sharedData = Preferences.get("ALL_CHARACTERS_DATA")

                when (response.value as NetworkResult) {
                    is NetworkResult.Success<*> -> {
                        response.value?.let {
                            data.clear()
                            it.data?.results?.forEach { item ->
                                data.add(
                                    CharactersListDataModel(
                                        id = item.id,
                                        name = item.name,
                                        status = item.status,
                                        species = item.species,
                                        type = item.type,
                                        gender = item.gender,
                                        image = item.image
                                    )
                                )
                            }
                            adapterData.value = data
                            Preferences.put(data, "ALL_CHARACTERS_DATA")
                            stateSuccess.value = SingleEvent(true)
                        }
                    }
                    is NetworkResult.Error<*> -> {
                        response.value?.let {
                            when (it.message.toString()) {

                                else -> {
                                    stateError.value = "Response error"
                                }
                            }
                        }
                    }
                    is NetworkResult.Loading<*> -> {

                    }
                }
            }
        }
    }

}