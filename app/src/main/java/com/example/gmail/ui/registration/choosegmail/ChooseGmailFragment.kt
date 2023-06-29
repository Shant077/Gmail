package com.example.gmail.ui.registration.choosegmail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gmail.R
import com.example.gmail.databinding.FragmentChooseGmailBinding
import com.example.gmail.extensions.collectWhileStarted
import com.example.gmail.ui.registration.birthdayandgender.BirthdayAndGenderViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChooseGmailFragment : Fragment() {

    private val viewModel by viewModel<ChooseGmailViewModel>()

    private var _binding: FragmentChooseGmailBinding? = null
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
        _binding = FragmentChooseGmailBinding.inflate(inflater, container, false)
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
                is ChooseGmailViewModel.RegistrationChooseEmailCommand.Idle -> {
                    hideLoading()
                }

                is ChooseGmailViewModel.RegistrationChooseEmailCommand.ShowLoading -> {
                    showLoading()
                }

                is ChooseGmailViewModel.RegistrationChooseEmailCommand.ShowEmptyInputError -> {
                    hideLoading()
                    Toast.makeText(
                        this.context,
                        "All fields must be filled",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ChooseGmailViewModel.RegistrationChooseEmailCommand.NavigateToHome -> {
                    findNavController().navigate(R.id.action_chooseGmailFragment_to_passwordFragment)
                }
            }
        }
    }


    private fun handleInputs() {
        binding.buttonNextToGmailInputPage.setOnClickListener {
            val dataMonth = binding.gmailInput.text.toString().trim()
            viewModel.registrationAttempt(dataMonth)
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