package com.example.rickandmorty.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.Application
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.ui.model.app_view_model.NavigationViewModel
import com.example.rickandmorty.utils.NetworkConnectionManager
import com.example.rickandmorty.utils.cicerone.Screens.main
import com.example.rickandmorty.utils.common.extensions.showCustomSnackBar
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
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

        checkInternet()

        navigationViewModel.onForwardCommandClick(main())
    }

    fun checkInternet() {

        lifecycleScope.launchWhenCreated {
            networkConnectionManager.isNetworkConnectedFlow
                .collectLatest {
                    if (!it) {
                        delay(1000)

                        val snackbar = binding.root.showCustomSnackBar(
                            getString(R.string.network_error_text),
                            R.drawable.ic_not_internet,
                            R.drawable.background_snack_bar_gradient
                        )
                        snackbar.show()
                    }
                }
        }
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