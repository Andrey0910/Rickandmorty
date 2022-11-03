package com.example.rickandmorty.utils

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

/**
 * Network Monitoring module
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkMonitoringModule {
    companion object {
        @Provides
        @Singleton
        fun provideCoroutineScope() = CoroutineScope(Dispatchers.Default + SupervisorJob())
    }

    @Binds
    abstract fun bindNetworkConnectionManager(
        networkConnectionManagerImpl: NetworkConnectionManagerImpl
    ): NetworkConnectionManager
}