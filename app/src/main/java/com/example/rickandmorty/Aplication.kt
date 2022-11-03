package com.example.rickandmorty

import android.app.Application
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import androidx.viewbinding.BuildConfig
import com.example.rickandmorty.utils.NetworkConnectionManager
import com.example.rickandmorty.utils.Preferences
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class Application : Application() {

    @Inject
    lateinit var networkConnectionManager: NetworkConnectionManager

    override fun onCreate() {
        super.onCreate()

        networkConnectionManager.startListenNetworkState()

        appContext = applicationContext

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        Preferences.with(this)
    }

    companion object{
        lateinit var appContext: Context
    }
}