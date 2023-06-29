package com.example.gmail.ui.passwordforsignin

import android.content.Context
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
import com.example.gmail.databinding.FragmentPasswordForSignInBinding
import com.example.gmail.extensions.collectWhileStarted
import com.example.gmail.ui.login.LoginViewModel
import com.example.gmail.ui.registration.phonenumber.PhoneNumberViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class PasswordForSignIn : Fragment() {

    private var _binding: FragmentPasswordForSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<PasswordSignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
        _binding = FragmentPasswordForSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPassword()
        handelInputs()
        observeViewModel()
        getEmail()
    }


    private fun observeViewModel() {
        viewModel.loginCommand.collectWhileStarted(viewLifecycleOwner) { command ->
            when (command) {
                is PasswordSignInViewModel.PasswordSignInCommand.Idle -> {
                    hideLoading()
                }

                is PasswordSignInViewModel.PasswordSignInCommand.ShowLoading -> {
                    showLoading()
                }

                is PasswordSignInViewModel.PasswordSignInCommand.ShowEmptyInputError -> {
                    hideLoading()
                    Toast.makeText(
                        this.context,
                        "password shouldn't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is PasswordSignInViewModel.PasswordSignInCommand.ShowWrongCredentialsError -> {
                    hideLoading()
                    Toast.makeText(this.context, "Invalid password", Toast.LENGTH_SHORT)
                        .show()
                }

                is PasswordSignInViewModel.PasswordSignInCommand.NavigateToHome -> {
                    findNavController().navigate(R.id.action_passwordForSignIn_to_homeFragment)
                }
            }
        }
    }


    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
    }

    private fun handelInputs() {
        binding.buttonPassword.setOnClickListener {
            val password = binding.passwordInput.text.toString().trim()
            viewModel.passwordAttempt(password)
        }
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

    private fun getEmail() {
        val sharedPreference =
            context?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val getEmail = sharedPreference?.getString("email", "default_value")!!
        binding.gmailName.text = getEmail
    }
}