package com.example.gmail.ui.phoneconfermation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmail.domain.phoneconfermation.PhoneConfirmationResult
import com.example.gmail.domain.phoneconfermation.PhoneConfirmationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PhoneConfirmationViewModel(private val phoneConfirmationUseCase: PhoneConfirmationUseCase) :ViewModel() {

    private val _phoneConfirmationData: Channel<PhoneConfirmationCommand> = Channel()
    val phoneConfirmation = _phoneConfirmationData.receiveAsFlow()

    init {
        viewModelScope.launch {
            _phoneConfirmationData.send(PhoneConfirmationCommand.Idle)
        }
    }

    fun registrationAttempt(pinCode: String) {
        viewModelScope.launch {
            _phoneConfirmationData.send(PhoneConfirmationCommand.ShowLoading)
            val command = when (phoneConfirmationUseCase.checkPinCode(pinCode)) {
                PhoneConfirmationResult.ERROR_WRONG_CREDENTIALS -> PhoneConfirmationCommand.ShowWrongCredentialsError
                PhoneConfirmationResult.SUCCESS -> PhoneConfirmationCommand.NavigateToHome
            }
            _phoneConfirmationData.send(command)
        }
    }
    sealed class PhoneConfirmationCommand {
        object Idle : PhoneConfirmationCommand()
        object ShowLoading : PhoneConfirmationCommand()
        object ShowWrongCredentialsError : PhoneConfirmationCommand()
        object NavigateToHome : PhoneConfirmationCommand()
    }
}