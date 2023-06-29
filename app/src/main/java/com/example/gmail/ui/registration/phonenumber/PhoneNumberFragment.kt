package com.example.gmail.ui.registration.phonenumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gmail.R
import com.example.gmail.databinding.FragmentPhoneNumberBinding
import com.example.gmail.extensions.collectWhileStarted
import com.example.gmail.ui.registration.registrationuser.RegistrationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hbb20.CountryCodePicker
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhoneNumberFragment : Fragment() {
    private var _binding: FragmentPhoneNumberBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<PhoneNumberViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
        _binding = FragmentPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleInputs()
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.registrationCommand.collectWhileStarted(viewLifecycleOwner) { command ->
            when (command) {
                is PhoneNumberViewModel.RegistrationPhoneNumberCommand.Idle -> {
                    hideLoading()
                }

                is PhoneNumberViewModel.RegistrationPhoneNumberCommand.ShowLoading -> {
                    showLoading()
                }

                is PhoneNumberViewModel.RegistrationPhoneNumberCommand.ShowEmptyInputError -> {
                    hideLoading()
                    Toast.makeText(
                        this.context,
                        "All fields must be filled",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is PhoneNumberViewModel.RegistrationPhoneNumberCommand.NavigateToHome -> {
                    findNavController().navigate(R.id.action_phoneNumberFragment_to_phoneConfermationFragment)
                }
            }
        }
    }


    private fun handleInputs() {
        binding.buttonPhoneNumber.setOnClickListener {
            val numberCode: EditText = binding.phoneNumberCode
            val numberCodeCountry: CountryCodePicker = binding.countyCodePicker
            val countryCode:String=numberCodeCountry.selectedCountryCode
            val codeNumber: String =numberCode.text.toString()
            val allNumber:String=countryCode+codeNumber
            viewModel.registrationAttempt(allNumber)
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