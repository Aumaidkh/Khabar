package com.snapp.khabar.feature_fetch_news.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.snapp.khabar.R
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.adapters.ViewPagerAdapter
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings.SettingsScreenEvent
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings.SettingsUiEvent
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings.SettingsViewModel
import com.snapp.khabar.feature_fetch_news.presentation.util.enableNightMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "HomeActivity"
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_layout)



        val navController = findNavController(R.id.myNavHost)
        val bottomNavView = findViewById<BottomNavigationView>(R.id.menuView)

        bottomNavView.setupWithNavController(navController)
        settingsViewModel.onEvent(SettingsScreenEvent.IsDarkModeEnabled)

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        collectFlows()
    }

    private fun collectFlows(){
        /**
         * Settings view model events
         * */
        lifecycleScope.launchWhenStarted {

            settingsViewModel.eventFlow.collect { event ->
                when(event){
                    is SettingsUiEvent.DarkModeToggle -> {
                        Log.d(TAG, "Apply Dark Mode: ${event.isEnabled}")
                        enableNightMode(event.isEnabled)
                    }
                }
            }


        }
    }
}
