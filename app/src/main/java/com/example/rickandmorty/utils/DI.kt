package com.example.rickandmorty.utils

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency injection - Hilt
 */

/**
 * Navigation module
 */

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule{
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRoute(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun providerNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}