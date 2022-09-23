package com.snapp.khabar.feature_fetch_news.presentation.ui.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.snapp.khabar.R
import com.snapp.khabar.databinding.ActivitySignUpLayoutBinding
import com.snapp.khabar.feature_fetch_news.core.DataBindingActivity
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "SignUpActivity"
@AndroidEntryPoint
class SignUpActivity :
    DataBindingActivity<ActivitySignUpLayoutBinding>(R.layout.activity_sign_up_layout) {

    /**
     * ViewModels
     * */
    private val viewModel: SignUpViewModel by viewModels()

    override fun ActivitySignUpLayoutBinding.initialize() {
        setupClicks()
        setupInputFields()
    }


    /**
     * Setting Up Click Listeners
     * */
    private fun setupClicks(){
        binding.apply {
            /**
             * Back Button*/
            btnBack.setOnClickListener {
                finish()
            }

            /**
             * Sign Up Button*/
            btnLogin.setOnClickListener {
                viewModel.onEvent(SignUpEvents.UserEvents.SignUpWithEmailAndPassword(
                    email = etEmail.text.toString(),
                    password = etPassword.text.toString(),
                    rePassword = etRePassword.text.toString()
                ))
            }

        }
    }

    /**
     * Collecting all the flows
     * */
    private fun consumeFlows(){
        lifecycleScope.launchWhenStarted {
            /**
             * Collecting State
             * */
            viewModel.state.collect { state ->
                if (state.isLoading == true){
                    // Show Progress Bar
                    Log.d(TAG, "Loading...")
                }else if(state.isLoading == false && !state.isUserAuthenticated){
                    // User Not Authenticated
                    Log.d(TAG, "User Not Autheticated")
                } else if (state.isLoading == false && state.isUserAuthenticated){
                    Log.d(TAG, "User is Authenticated")
                }
                updateUi(state)
            }
        }

        lifecycleScope.launchWhenStarted {
            /**
             * Collecting Events
             * */
            viewModel.eventFlow.collectLatest { event ->
                when(event){
                    is SignUpEvents.UiEvents.ShowSnackBar -> {
                        Snackbar.make(binding.root,event.message,Snackbar.LENGTH_SHORT).show()
                    }

                    is SignUpEvents.UiEvents.NavigateUserToHomeActivity -> {
                        Intent(this@SignUpActivity,HomeActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        }.also {
                            startActivity(it)
                        }
                    }
                }
            }
        }
    }

    /**
     * Setting up Input Fields
     * */
    private fun setupInputFields() {
        binding.apply {
            etEmail.doOnTextChanged{ text, _, _, _ ->
                viewModel.onEvent(SignUpEvents.UserEvents.OnEmailChanged(text.toString()))
            }

            etPassword.doOnTextChanged{ text, _, _, _ ->
                viewModel.onEvent(SignUpEvents.UserEvents.OnPasswordChanged(text.toString()))
            }

            etRePassword.doOnTextChanged{ text, _, _, _ ->
                viewModel.onEvent(SignUpEvents.UserEvents.OnReEnterPasswordChanged(text.toString()))
            }
        }
    }

    /**
     * Updates Ui
     * */
    private fun updateUi(state: SignUpState){
        updateErrors(state)
    }


    /**
     * Update Errors On Input Fields
     * */
    private fun updateErrors(state: SignUpState){
        binding.apply {
            etEmail.error = state.emailError
            etPassword.error = state.passwordError
            etRePassword.error = state.rePasswordError
        }
    }

    override fun onResume() {
        super.onResume()
        consumeFlows()
    }
}