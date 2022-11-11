package com.example.rickandmorty.ui.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.ui.model.app_view_model.NavigationViewModel
import com.example.rickandmorty.utils.NetworkConnectionManager
import com.example.rickandmorty.utils.cicerone.Screens.main
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var networkConnectionManager: NetworkConnectionManager

    private val navigationViewModel by viewModels<NavigationViewModel>()

    private val navigator: Navigator = object : AppNavigator(this, R.id.main_container) {

        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navigationViewModel.onForwardCommandClick(main())

//        lifecycleScope.launchWhenCreated {
//            repeatOnLifecycle(Lifecycle.State.RESUMED){
//                networkConnectionManager.isNetworkConnectedFlow.collectLatest {
//                    Timber.tag("network status").d(it.toString())
//                    Log.i(TAG, "AAA mainActibity - network status")
//                    navigationViewModel.onReplaceCommandClick(main())
//                }
//            }
//        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}