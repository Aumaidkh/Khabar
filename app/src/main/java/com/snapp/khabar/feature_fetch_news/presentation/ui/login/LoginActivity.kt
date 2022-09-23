package com.snapp.khabar.feature_fetch_news.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.snapp.khabar.R
import com.snapp.khabar.databinding.ActivityLoginBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.HomeActivity
import com.snapp.khabar.feature_fetch_news.presentation.ui.sign_up.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    /**
     * Binding Vars
     * */
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModels
     * */
    private val loginViewModel: LoginViewModel by viewModels()

    /**
     * Other Vars
     * */
    private val googleSignInResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val googleSignInAccount = task.getResult(ApiException::class.java)
                if (googleSignInAccount != null) {
                    getGoogleAuthCredentials(googleSignInAccount)
                }
            } catch (e: ApiException) {

            }
        }


    /**
     * Auth Vars
     * */
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.splashScreenTheme)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        initSignInButton()
        initGoogleSignInClient()

        /**
         * Check if user is already logged in
         * */
        loginViewModel.onEvent(LoginEvents.CheckIfUserIsAlreadyAuthenticated)
        setupClicks()
    }

    private fun setupClicks(){
        /**
         * Sign Up Click Button
         * */
        binding.btnSignUp.setOnClickListener {
            loginViewModel.onEvent(LoginEvents.SignUpClick)
        }
    }

    private fun initSignInButton() {
        binding.btnGoogleLayout.setOnClickListener {
            signIn()
        }
    }

    private fun initGoogleSignInClient() {
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }


    private fun signIn() {
        googleSignInClient.signInIntent.also {
            googleSignInResult.launch(it)
        }
    }

    private fun getGoogleAuthCredentials(googleSignInAccount: GoogleSignInAccount) {
        val googleTokenId = googleSignInAccount.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        signInWithGoogleAuthCredential(googleAuthCredential)
    }

    private fun signInWithGoogleAuthCredential(authCredential: AuthCredential) {
        loginViewModel.onEvent(LoginEvents.Login(authCredential = authCredential))
    }

    private fun consumeFlows(){
        lifecycleScope.launchWhenStarted {
            /**
             * Observing Events
             * */
            loginViewModel.eventFlow.collect{ event ->
                when(event){
                    is LoginUiEvents.ShowSnackBar -> {
                        Snackbar.make(binding.root,event.message,Snackbar.LENGTH_SHORT).show()
                    }

                    is LoginUiEvents.NavigateToSignUpScreen -> {
                        Intent(this@LoginActivity,SignUpActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                }
            }

        }

        /**
         * Observing State
         * */
        lifecycleScope.launchWhenStarted {
            loginViewModel.state.collect { state ->
                if (state.isLoading){
                    Log.d(TAG, "consumeFlows: Loading")
                    binding.progressBar.visibility = View.VISIBLE
                } else if (!state.isAuthenticated){
                    Log.d(TAG, "consumeFlows: Not Authenticated")
                    binding.progressBar.visibility = View.GONE
                } else if(state.isAuthenticated) {
                    Log.d(TAG, "consumeFlows: Authenticated")
                    binding.progressBar.visibility = View.GONE
                    Intent(this@LoginActivity,HomeActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        consumeFlows()
    }


}

private const val TAG = "LoginActivity"