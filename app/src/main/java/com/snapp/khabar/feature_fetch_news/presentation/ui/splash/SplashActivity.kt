package com.snapp.khabar.feature_fetch_news.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.snapp.khabar.R
import com.snapp.khabar.databinding.ActivitySplashBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.HomeActivity
import com.snapp.khabar.feature_fetch_news.presentation.ui.login.LoginActivity
import com.snapp.khabar.feature_fetch_news.presentation.ui.login.LoginEvents
import com.snapp.khabar.feature_fetch_news.presentation.ui.login.LoginUiEvents
import com.snapp.khabar.feature_fetch_news.presentation.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

private const val TAG = "SplashActivity"
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    /**
     * DataBinding Vars
     * */
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding

    /**
     * ViewModels
     * */
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        consumeFlows()

        checkIfUserIsAlreadyAuthenticated()

    }

    private fun checkIfUserIsAlreadyAuthenticated(){
        viewModel.onEvent(LoginEvents.CheckIfUserIsAlreadyAuthenticated)
    }

    /**
     * Collecting flows here
     * */
    private fun consumeFlows(){

        /**
         * Collecting state
         * */
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect{ event ->
                when(event){
                    is LoginUiEvents.UserNotAuthenticated -> {
                        navigateUserToActivity(LoginActivity::class.java)
                    }

                    is LoginUiEvents.UserAuthenticated -> {
                        navigateUserToActivity(HomeActivity::class.java)
                    }

                    is LoginUiEvents.ShowSnackBar -> {
                        Snackbar.make(binding!!.root,event.message,Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    /**
     * Navigates the user to the activity
     * @param activity
     * */
    private fun navigateUserToActivity(activity: Class<*>) {
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, activity).also {
                startActivity(it)
                finish()
            }
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}