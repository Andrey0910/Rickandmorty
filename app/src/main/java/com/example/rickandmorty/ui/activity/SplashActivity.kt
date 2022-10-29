package com.example.rickandmorty.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.databinding.ActivitySplashBinding
import com.example.rickandmorty.utils.NetworkConnectionManager
import com.github.terrakok.cicerone.NavigatorHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

//    @Inject
//    lateinit var networkConnectionManager: NetworkConnectionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launchWhenCreated {
            delay(2000)
//            checkInternet()
        }
    }

//    fun checkInternet(){
//        lifecycleScope.launchWhenCreated {
//            networkConnectionManager.isNetworkConnectedFlow.collectLatest {
//                delay(1000)
//                if (it){
//
//                }
//            }
//        }
//    }
}