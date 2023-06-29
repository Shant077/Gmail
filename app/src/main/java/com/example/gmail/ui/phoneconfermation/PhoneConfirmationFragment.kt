package com.example.gmail.ui.phoneconfermation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gmail.R
import com.example.gmail.databinding.FragmentPhoneConfermationBinding
import com.example.gmail.extensions.collectWhileStarted
import com.example.gmail.ui.registration.birthdayandgender.BirthdayAndGenderViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class PhoneConfirmationFragment : Fragment() {

    private val viewModel by viewModel<PhoneConfirmationViewModel>()

    private var _binding: FragmentPhoneConfermationBinding? = null
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
        _binding = FragmentPhoneConfermationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleInputs()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.phoneConfirmation.collectWhileStarted(viewLifecycleOwner) { command ->
            when (command) {
                is PhoneConfirmationViewModel.PhoneConfirmationCommand.Idle -> {
                    hideLoading()
                }

                is PhoneConfirmationViewModel.PhoneConfirmationCommand.ShowLoading -> {
                    showLoading()
                }

                is PhoneConfirmationViewModel.PhoneConfirmationCommand.ShowWrongCredentialsError -> {
                    hideLoading()
                    Toast.makeText(this.context, "Invalid pin code", Toast.LENGTH_SHORT)
                        .show()
                }

                is PhoneConfirmationViewModel.PhoneConfirmationCommand.NavigateToHome -> {
                    findNavController().navigate(R.id.action_phoneConfermationFragment_to_homeFragment)
                }
            }
        }
    }

    private fun handleInputs() {
        binding.buttonPinCode.setOnClickListener {
            val phoneCode = binding.txtPinEntry.text.toString().trim()
            viewModel.registrationAttempt(phoneCode)
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