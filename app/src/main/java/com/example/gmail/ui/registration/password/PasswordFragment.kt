package com.example.gmail.ui.registration.password

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gmail.R
import com.example.gmail.databinding.FragmentPasswordBinding
import com.example.gmail.extensions.collectWhileStarted
import com.example.gmail.ui.registration.registrationuser.RegistrationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class PasswordFragment : Fragment() {
    private val viewModel by viewModel<PasswordViewModel>()
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPassword()
        handleInputs()
        observeViewModel()
    }

    //
//
    private fun observeViewModel() {
        viewModel.registrationCommand.collectWhileStarted(viewLifecycleOwner) { command ->
            when (command) {
                is PasswordViewModel.RegistrationPasswordCommand.Idle -> {
                    hideLoading()
                }

                is PasswordViewModel.RegistrationPasswordCommand.ShowLoading -> {
                    showLoading()
                }

                is PasswordViewModel.RegistrationPasswordCommand.LengthInputError -> {
                    hideLoading()
                    Toast.makeText(
                        this.context,
                        "The number of characters password must exceed 6",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is PasswordViewModel.RegistrationPasswordCommand.ShowEmptyInputError -> {
                    hideLoading()
                    Toast.makeText(
                        this.context,
                        "all fields must be filled",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is PasswordViewModel.RegistrationPasswordCommand.NavigateToHome -> {
                    findNavController().navigate(R.id.action_passwordFragment_to_phoneNumberFragment)
                }

            }
        }
    }

    private fun handleInputs() {
        binding.buttonPassword.setOnClickListener {
            val password = binding.passwordInput.text.toString().trim()
            viewModel.registrationAttempt(password)
        }
    }

    private fun showLoading() {
        binding.loginGroup.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
        binding.loginGroup.isVisible = true
    }

    private fun showPassword() {
        binding.radioBarShowPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.passwordInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }
}