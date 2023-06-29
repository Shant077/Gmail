package com.example.gmail.ui.registration.birthdayandgender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gmail.R
import com.example.gmail.databinding.FragmentBirthdayAndGenderBinding
import com.example.gmail.extensions.collectWhileStarted
import com.example.gmail.ui.registration.registrationuser.RegistrationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class BirthdayAndGenderFragment : Fragment() {
    private val viewModel by viewModel<BirthdayAndGenderViewModel>()

    private var _binding: FragmentBirthdayAndGenderBinding? = null
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
        _binding = FragmentBirthdayAndGenderBinding.inflate(inflater, container, false)
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
                is BirthdayAndGenderViewModel.RegistrationBirthdayAndGenderCommand.Idle -> {
                    hideLoading()
                }

                is BirthdayAndGenderViewModel.RegistrationBirthdayAndGenderCommand.ShowLoading -> {
                    showLoading()
                }

                is BirthdayAndGenderViewModel.RegistrationBirthdayAndGenderCommand.ShowEmptyInputError -> {
                    hideLoading()
                    Toast.makeText(
                        this.context,
                        "All fields must be filled",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is BirthdayAndGenderViewModel.RegistrationBirthdayAndGenderCommand.NavigateToHome -> {
                    findNavController().navigate(R.id.action_birthdayAndGenderFragment_to_chooseGmailFragment)
                }
            }
        }
    }


    private fun handleInputs() {
        binding.buttonNextToGmailChoosePage.setOnClickListener {
            val dataMonth = binding.dataMonth.text.toString().trim()
            val dataDay = binding.dataDay.text.toString().trim()
            val dataYear = binding.dataYear.text.toString().trim()
            val gender = binding.gender.text.toString().trim()
            viewModel.registrationAttempt(dataMonth, dataDay, dataYear, gender)
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