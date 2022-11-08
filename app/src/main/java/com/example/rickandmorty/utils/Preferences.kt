package com.example.rickandmorty.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Класс сохранения данных во внутреннее хранилище
 */
object Preferences {

    lateinit var preferences: SharedPreferences
    private const val settingsNamePrivate = "private_app_options"

    fun with(application: Application) {
        preferences = application.getSharedPreferences(
            settingsNamePrivate, Context.MODE_PRIVATE)
    }

    // Сохранить данные
    fun setPrefsStringUserData(key: String?, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun setPrefsBooleanUserData(key: String?, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun setPrefsIntUserData(key: String?, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun <T> put(`object`: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        preferences.edit().putString(key, jsonString).apply()
    }

    // Получить данные
    fun getPrefsStringUserData(key: String): String {
        return preferences.getString(key, "").toString()
    }

    fun getPrefsBooleanUserData(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getPrefsIntUserData(key: String): Int {
        return preferences.getInt(key, 999)
    }

    inline fun <reified T : Any> get(key: String): ArrayList<T>? {
        val value = preferences.getString(key, "")
        val listType: Type = object : TypeToken<ArrayList<T>>() {}.type
        return Gson().fromJson(value, listType)
    }

    // удаление настроек
    fun deletePreferences(vararg name: String) {
        for (value in name) {
            preferences.edit().clear().apply()
        }
    }
}