package com.example.rickandmorty.utils

/**
 * интерфейс для перехвата системной кнопки назад
 */
interface BackButtonListener {
    fun onBackPressed(): Boolean
}