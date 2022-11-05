package com.example.rickandmorty.ui.model.app_view_model

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScrollViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val recycleItemPosition: MutableLiveData<Parcelable> = MutableLiveData()
    val recycleItemPositionView: LiveData<Parcelable> = recycleItemPosition

    fun saveRecycleItemPosition(state: Parcelable) {
        recycleItemPosition.value = state
    }

}