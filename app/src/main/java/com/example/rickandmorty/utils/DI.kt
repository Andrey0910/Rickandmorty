package com.example.rickandmorty.utils

import android.content.Context
import com.example.rickandmorty.Application
import com.example.rickandmorty.data.api.NetworkService
import com.example.rickandmorty.utils.Constants.BASE_URL
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Dependency injection - Hilt
 */

/**
 * Application module
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext application: Context): Application {
        return application as Application
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}

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

/**
 * Retrofit module
 */
@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }
}