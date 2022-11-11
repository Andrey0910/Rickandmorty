package com.example.rickandmorty.ui.model.request_view_model

import android.content.ContentValues.TAG
import android.util.Log
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private var sharedData: ArrayList<CharactersListDataModel>? =
        Preferences.get("ALL_CHARACTERS_DATA")

    init {
        if (!sharedData.isNullOrEmpty()) {
            charactersList()
        } else {
            viewModelScope.launch {
                stateLoading.value = SingleEvent(true)
                delay(500)
                charactersList()
            }
        }
    }

    private fun charactersList() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                repository.getCharacters().collect { values ->
                    response.value = values
                }

                sharedData = Preferences.get("ALL_CHARACTERS_DATA")

                when (response.value as NetworkResult) {

                    is NetworkResult.Success<*> -> {

                        response.value?.let {

                            data.clear()

                            it.data?.results?.forEach { item ->

                                var isFavorite = false

                                sharedData?.filter { value -> value.id == item.id }
                                    ?.map { mapValue ->
                                        isFavorite = mapValue.favorite
                                    }

                                data.add(
                                    CharactersListDataModel(
                                        id = item.id,
                                        name = item.name,
                                        status = item.status,
                                        species = item.species,
                                        type = item.type,
                                        gender = item.gender,
                                        image = item.image,
                                        favorite = isFavorite,
                                        name_location = item.location.name
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

    private fun updateRecycler() {

        sharedData = Preferences.get("ALL_CHARACTERS_DATA")

        val convertSharedData = arrayListOf<Item>()
        sharedData.let {
            it?.forEach { value ->
                convertSharedData.add(
                    CharactersListDataModel(
                        id = value.id,
                        name = value.name,
                        status = value.status,
                        species = value.species,
                        type = value.type,
                        gender = value.gender,
                        image = value.image,
                        favorite = value.favorite,
                        name_location = value.name_location
                    )
                )
            }
        }

        adapterData.value = convertSharedData
    }

    private fun updateShareData(item: CharactersListDataModel) {

        sharedData = Preferences.get("ALL_CHARACTERS_DATA")

        if (!sharedData.isNullOrEmpty()) {
            sharedData?.forEachIndexed { index, id ->
                id.takeIf { it.id == item.id }.let {
                    sharedData?.let {
                        it[index] = item.copy(favorite = true)
                    }
                }
            }

            Preferences.put(sharedData, "ALL_CHARACTERS_DATA")

            updateRecycler()
        }
    }

    fun updateCharactersList() {
        stateLoading.value = SingleEvent(true)
        charactersList()
    }

}