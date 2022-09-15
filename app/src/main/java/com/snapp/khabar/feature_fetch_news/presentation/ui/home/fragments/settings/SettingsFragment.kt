package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.snapp.khabar.R
import com.snapp.khabar.databinding.FragmentSettingsBinding
import com.snapp.khabar.feature_fetch_news.presentation.ui.comment.CommentsActivity
import com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account.EditAccountActivity
import com.snapp.khabar.feature_fetch_news.presentation.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine

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
    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

            editProfile.setOnClickListener {
                settingsViewModel.onEvent(SettingsScreenEvent.EditProfile)
            }

            switch1.setOnCheckedChangeListener { _, isEnabled ->
                Log.d("TAG", "onCreateView: isOn: $isEnabled")
                if (isEnabled) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
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
                    Log.d(TAG, "consumeFlows: Loading")
                } else {
                    Log.d(TAG, "consumeFlows: Success")
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
                        findNavController().navigate(R.id.action_settings_to_editAccountActivity)
                    }
                }
            }
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
            Log.d(TAG, "updateUi: ${state.imageUrl}")

        }
    }

    override fun onResume() {
        super.onResume()
        settingsViewModel.onEvent(SettingsScreenEvent.InitScreenState)
        consumeFlows()
    }

}

private const val TAG = "SettingsFragment"