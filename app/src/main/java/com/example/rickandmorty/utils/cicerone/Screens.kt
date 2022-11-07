package com.example.rickandmorty.utils.cicerone

import android.content.Intent
import com.example.rickandmorty.ui.activity.MainActivity
import com.example.rickandmorty.ui.activity.SplashActivity
import com.example.rickandmorty.ui.fragment.MainFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun splash() = ActivityScreen{
        Intent(it, SplashActivity::class.java)
    }

    fun start() = ActivityScreen{
        Intent(it, MainActivity::class.java)
    }

    fun main() = FragmentScreen {
        MainFragment.newInstance()
    }
}