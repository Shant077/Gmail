package com.example.gmail.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gmail.MainActivity
import com.example.gmail.R
import com.example.gmail.databinding.FragmentLoginBinding
import com.example.gmail.extensions.collectWhileStarted
import com.example.gmail.ui.phoneconfermation.PhoneConfirmationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private val viewModel by viewModel<LoginViewModel>()

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActivityLink()
        changePageToRegistrationFragment()
        handleInputs()
        observeViewModel()
    }

    private fun setupActivityLink() {
        val linkTextView = binding.textForLearnMore
        linkTextView.movementMethod = LinkMovementMethod.getInstance()
        linkTextView.setLinkTextColor(Color.BLUE)
    }

    private fun observeViewModel() {
        viewModel.loginCommand.collectWhileStarted(viewLifecycleOwner) { command ->
            when (command) {
                is LoginViewModel.LoginCommand.Idle -> {
                    hideLoading()
                }

                is LoginViewModel.LoginCommand.ShowLoading -> {
                    showLoading()
                }

                is LoginViewModel.LoginCommand.ShowEmptyInputError -> {
                    hideLoading()
                    Toast.makeText(
                        this.context,
                        "Username and password shouldn't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is LoginViewModel.LoginCommand.ShowWrongCredentialsError -> {
                    hideLoading()
                    Toast.makeText(this.context, "Invalid email", Toast.LENGTH_SHORT)
                        .show()
                }

                is LoginViewModel.LoginCommand.NavigateToHome -> {
                    findNavController().navigate(R.id.action_loginFragment_to_passwordForSignIn)
                }
            }
        }
    }


    private fun changePageToRegistrationFragment() {
        binding.createAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun handleInputs() {
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            viewModel.loginAttempt(email)
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

}