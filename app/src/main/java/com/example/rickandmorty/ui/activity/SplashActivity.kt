package com.example.rickandmorty.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmorty.databinding.ActivitySplashBinding
import com.example.rickandmorty.ui.app_view_model.NavigationViewModel
import com.example.rickandmorty.utils.cicerone.Screens.start
import com.github.terrakok.cicerone.NavigatorHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navigationViewModel.onReplaceCommandClick(start())
    }
}