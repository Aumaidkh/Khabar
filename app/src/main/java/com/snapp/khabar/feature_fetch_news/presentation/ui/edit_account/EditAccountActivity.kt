package com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.snapp.khabar.R
import com.snapp.khabar.databinding.ActivityEditAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditAccountActivity: AppCompatActivity() {

    /**
     * DataBinding Vars
     * */
    private var _binding: ActivityEditAccountBinding? = null
    private val binding get() = _binding!!


    /**
     * ViewModels
     * */
    private val profileViewModel: ProfileViewModel by viewModels()

    /**
     * Other Vars
     * */



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_account)

        setupClicks()

        consumeFlows()

        setUpInputFields()

    }

    private fun consumeFlows(){
        // Collecting State
        lifecycleScope.launchWhenStarted {
            profileViewModel.state.collect { _state ->
                showErrorsIfAny(_state)
                showSelectedGenderButton(_state)
            }
        }
    }

    private fun showSelectedGenderButton(_state: ProfileState) {
        binding.apply {
            if (_state.gender.name == GenderEnum.Male.name){
                btnMale.strokeWidth = 2
                btnFemale.strokeWidth = 0
            } else {
                btnMale.strokeWidth = 0
                btnFemale.strokeWidth = 2
            }
        }
    }

    private fun setUpInputFields(){
        binding.apply {
            // Name Field
            etName.doOnTextChanged { text, _, _, _ ->
                profileViewModel.onEvent(EditProfileEvents.OnNameChange(text.toString()))
            }

            // Email Field
            etEmail.doOnTextChanged { text, _, _, _ ->
                profileViewModel.onEvent(EditProfileEvents.OnEmailChange(text.toString()))
            }

            // Phone Field
            etPhone.doOnTextChanged { text, _, _, _ ->
                profileViewModel.onEvent(EditProfileEvents.OnPhoneChanged(text.toString()))
            }

            // On Gender Change
            setupGenderButtons()
        }
    }

    private fun showErrorsIfAny(state: ProfileState){
        binding.apply {
            etName.error = state.nameError
            etEmail.error = state.emailError
            etPhone.error = state.phoneError
        }
    }

    private fun setupGenderButtons(){
        binding.apply {
            btnMale.setOnClickListener {
                profileViewModel.onEvent(EditProfileEvents.OnGenderChange(GenderEnum.Male))
            }
            btnFemale.setOnClickListener {
                profileViewModel.onEvent(EditProfileEvents.OnGenderChange(GenderEnum.Female))
            }
        }
    }

    private fun setupClicks() {
        binding.apply {
            btnBack.setOnClickListener{
                finish()
            }

            btnSaveChanges.setOnClickListener {
                profileViewModel.onEvent(EditProfileEvents.SaveChanges)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
