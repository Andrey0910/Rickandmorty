package com.example.rickandmorty.ui.model.app_view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Модель для управления навигацией
 */
@HiltViewModel
class NavigationViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    @Inject
    lateinit var router: Router

    fun onForwardCommandClick(screen: Screen){
        router.navigateTo(screen)
    }

    fun onBackCommandClick(){
        router.exit()
    }

    fun onBackToCommandClick(screen: Screen){
        router.backTo(screen)
    }

    fun onReplaceCommandClick(screen: Screen){
        router.replaceScreen(screen)
    }
}