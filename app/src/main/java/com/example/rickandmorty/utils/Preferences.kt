package com.example.rickandmorty.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * Класс сохранения данных во внутреннее хранилище
 */
object Preferences {
    lateinit var preferences: SharedPreferences
    private const val settingsNamePrivate = "private_app_options"

    fun wiht(application: Application){
        preferences = application.getSharedPreferences(
            settingsNamePrivate, Context.MODE_PRIVATE
        )
    }
}