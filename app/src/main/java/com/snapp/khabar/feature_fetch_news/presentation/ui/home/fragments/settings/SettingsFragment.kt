package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.snapp.khabar.R
import com.snapp.khabar.databinding.FragmentSettingsBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.login.LoginActivity
import com.snapp.khabar.feature_fetch_news.presentation.util.enableNightMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    /**
     * Data Binding
     * */
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModels
     * */
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )

        setupClicks()

        return binding.root
    }


    private fun setupClicks() {
        binding.apply {
            // Back
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            /**
             * Logout Button*/
            tvLogout.setOnClickListener {
                showAreYouSureToLogOutDialog()
            }


            /**
             * Edit Profile Click*/
            editProfile.setOnClickListener {
                settingsViewModel.onEvent(SettingsScreenEvent.EditProfile)
            }


            /**
             * Privacy Policy Click*/
            privacyPolicy.setOnClickListener {
                settingsViewModel.onEvent(SettingsScreenEvent.PrivacyPolicyClickEvent)
            }

            /**
             * Email Click*/
            eMail.setOnClickListener {
                settingsViewModel.onEvent(SettingsScreenEvent.EmailClickEvent)
            }

            /**
             * Phone Click*/
            mobile.setOnClickListener {
                settingsViewModel.onEvent(SettingsScreenEvent.PhoneNumberClickEvent)
            }

            /**
             * Dark Mode Switch
             * */
            darkModeSwitch.setOnCheckedChangeListener { _, isEnabled ->
                settingsViewModel.onEvent(SettingsScreenEvent.ApplyDarkMode(isEnabled))
            }
        }
    }



    private fun consumeFlows() {
        Log.d(TAG, "consumeFlows: ")
        /**
         * Observing state
         * */
        lifecycleScope.launchWhenStarted {
            settingsViewModel.state.collect { state ->
                if (state.isLoading) {
                    // SHow Loading
                 //   Log.d(TAG, "consumeFlows: Loading")
                } else {
                  //  Log.d(TAG, "consumeFlows: Success")
                    updateUi(state)
                }
            }
        }

        /**
         * Observing events
         * */
        lifecycleScope.launchWhenStarted {
            settingsViewModel.eventFlow.collect { event ->
                when (event) {
                    /**
                     * User Logout*/
                    is SettingsUiEvent.NavigateUserToLoginScreen -> {
                        Intent(context, LoginActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }.also {
                            startActivity(it)
                        }
                    }

                    /**
                     * Navigate User To Edit Profile
                     * */
                    is SettingsUiEvent.NavigateToEditProfileScreen -> {
                        Log.d(TAG, "Navigating to Edit Profile Screen")
                        findNavController().navigate(R.id.action_settings_to_editAccountActivity)
                    }


                    /**
                     * Privacy Policy Event*/
                    is SettingsUiEvent.PrivacyPolicyEvent -> {
                        openPrivacyPolicyLinkInBrowser(event.url)
                    }

                    /**
                     * Handle Email Click*/
                    is SettingsUiEvent.EmailEvent -> {
                        openEmailIntent(event.email)
                    }
                    /**
                     * Handle Phone Click*/
                    is SettingsUiEvent.PhoneEvent -> {
                        openPhoneIntent(event.phone)
                    }

                    /**
                     * Handle Dark Mode Light Mode State
                     * */
                    is SettingsUiEvent.DarkModeToggle -> {
                        activity?.enableNightMode(isEnabled = event.isEnabled)
                    }
                }
            }
        }
    }


    /**
     * Opens Up Email Intent With Prefilled email address
     * @param email*/
    private fun openEmailIntent(email: String){
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }.also {
            startActivity(Intent.createChooser(it,"Select Email"))
        }
    }


    /**
     * Opens up dialer intent with prefilled phone number
     * @param phone*/
    private fun openPhoneIntent(phone: String){
        Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$phone".toUri()
        }.also {
            startActivity(it)
        }
    }


    /**
     * Opens Up Privacy Policy Url in New Browser Window
     * */
    private fun openPrivacyPolicyLinkInBrowser(link: String) {
        Intent(Intent.ACTION_VIEW).apply {
            data = link.toUri()
        }.also {
            startActivity(it)
        }
    }


    /**
     * Shows a confirmation dialog for logging a user out
     * */
    private fun showAreYouSureToLogOutDialog() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(resources.getString(R.string.logout))
            setMessage(resources.getString(R.string.are_you_sure_to))
            setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                settingsViewModel.onEvent(SettingsScreenEvent.SignOut)
            }
            setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }


    /**
     * Update the widgets on Ui
     * */
    private fun updateUi(state: SettingState) {
        binding.apply {
            /*Show Profile Pic*/
            Glide.with(this@SettingsFragment)
                .load(state.imageUrl)
                .placeholder(R.drawable.dp)
                .error(R.drawable.dp)
                .into(ivProfilePic)

            tvName.text = state.username
            tvEmail.text = state.email
           // Log.d(TAG, "updateUi: ${state.imageUrl}")

        }
    }

    override fun onResume() {
        super.onResume()
        settingsViewModel.onEvent(SettingsScreenEvent.InitScreenState)
        consumeFlows()
    }

}

private const val TAG = "SettingsFragment"