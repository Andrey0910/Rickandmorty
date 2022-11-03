package com.example.rickandmorty.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.ui.model.CharactersListViewModel
import com.example.rickandmorty.utils.NetworkConnectionManager
import com.github.terrakok.cicerone.NavigatorHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var networkConnectionManager: NetworkConnectionManager


    private val charactersListViewModel by viewModels<CharactersListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.clRoot.setBackgroundColor(Color.RED)


        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                networkConnectionManager.isNetworkConnectedFlow.collectLatest {
                    delay(3000)
                    Timber.tag("network status").d(it.toString())
//                    binding.llNoInternet.isVisible = !it
                }
            }
        }
    }
}