package com.example.gmail.ui.registration.registrationuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gmail.R
import com.example.gmail.databinding.FragmentRegistrationBinding
import com.example.gmail.extensions.collectWhileStarted
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.core.component.KoinComponent
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegistrationFragment : Fragment(), KoinComponent {

    private val viewModel by viewModel<RegistrationViewModel>()

    private var _binding: FragmentRegistrationBinding? = null

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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
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
                is RegistrationViewModel.RegistrationCommand.Idle -> {
                    hideLoading()
                }

                is RegistrationViewModel.RegistrationCommand.ShowLoading -> {
                    showLoading()
                }

                is RegistrationViewModel.RegistrationCommand.ShowEmptyInputError -> {
                    hideLoading()
                    Toast.makeText(
                        this.context,
                        "All fields must be filled",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is RegistrationViewModel.RegistrationCommand.NavigateToHome -> {
                    findNavController().navigate(R.id.action_registrationFragment_to_birthdayAndGenderFragment)
                }
            }
        }
    }


    private fun handleInputs() {
        binding.buttonNextToBirthPage.setOnClickListener {
            val firstName = binding.firstName.text.toString().trim()
            val lastName = binding.lastName.text.toString().trim()
            viewModel.registrationAttempt(firstName, lastName)
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