package com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.snapp.khabar.R
import com.snapp.khabar.databinding.ActivityEditAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "EditAccountActivity"

@AndroidEntryPoint
class EditAccountActivity : AppCompatActivity() {

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

    /**
     * Photo Activity on Result
     * */
    private var photoIntentResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                profileViewModel.onEvent(
                    EditProfileEvents.OnProfilePicChanged(
                        data?.data.toString().toUri()
                    )
                )
                showPreviewOnImageView(data?.data.toString())
            }
        }

    private fun showPreviewOnImageView(uri: String) {
        Glide.with(this)
            .load(uri)
            .into(binding.ivProfilePic)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_account)

        setupClicks()

        consumeFlows()

        setUpInputFields()

    }

    override fun onResume() {
        super.onResume()
        profileViewModel.onEvent(EditProfileEvents.PrePopulateInputFields)
    }


    private fun consumeFlows() {
        // Collecting State
        lifecycleScope.launchWhenStarted {
            /**
             * Collecting State
             * */
            profileViewModel.state.collect { _state ->
                showErrorsIfAny(_state)
                showSelectedGenderButton(_state)
            }
        }

        lifecycleScope.launchWhenStarted {
            /**
             * Collecting Ui Events
             * */
            profileViewModel.eventFlow.collect { event ->
                when (event) {
                    is EditProfileEvents.UiEvents.ShowSnackBar -> {
                        Snackbar.make(
                            binding.root,
                            event.message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is EditProfileEvents.UiEvents.ProfileUpdated -> {
                        finish()
                    }

                    is EditProfileEvents.UiEvents.ShowToast -> {
                        Toast.makeText(
                            this@EditAccountActivity,
                            event.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    is EditProfileEvents.UiEvents.PrepopulateInputs -> {
                        Log.d(TAG, "Gender: ${event.gender}")
                        prePopulateInputFields(
                            name = event.name,
                            phone = event.phone,
                            email = event.email,
                            imageUri = event.imageUri.toString(),
                            gender = event.gender
                        )
                    }

                }
            }
        }
    }


    private fun prePopulateInputFields(
        name: String,
        phone: String,
        email: String,
        imageUri: String,
        gender: GenderEnum
    ) {
        binding.apply {
            etName.setText(name)
            etPhone.setText(phone)
            etEmail.setText(email)
            Glide.with(this@EditAccountActivity)
                .load(imageUri)
                .into(ivProfilePic)
            if (gender.name == GenderEnum.Male.name) {
                btnMale.strokeWidth = 2
                btnFemale.strokeWidth = 0
            } else {
                btnMale.strokeWidth = 0
                btnFemale.strokeWidth = 2
            }

        }

    }

    private fun showSelectedGenderButton(_state: ProfileState) {
        binding.apply {
            if (_state.gender?.name == GenderEnum.Male.name) {
                btnMale.strokeWidth = 2
                btnFemale.strokeWidth = 0
            } else {
                btnMale.strokeWidth = 0
                btnFemale.strokeWidth = 2
            }
        }
    }

    private fun setUpInputFields() {
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

    private fun showErrorsIfAny(state: ProfileState) {
        binding.apply {
            etName.error = state.nameError
            etEmail.error = state.emailError
            etPhone.error = state.phoneError
        }
    }

    private fun setupGenderButtons() {
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
            // Back Button
            btnBack.setOnClickListener {
                finish()
            }

            // Save Changes Button
            btnSaveChanges.setOnClickListener {
                profileViewModel.onEvent(EditProfileEvents.SaveChanges)
            }

            // Change Profile Pic Button
            ivProfilePic.setOnClickListener {
                openPhotoPicker()
            }
        }
    }


    private fun openPhotoPicker() {
        Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }.also {
            photoIntentResultLauncher.launch(it)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
