package com.example.gmail.ui.registration.registrationuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmail.domain.registration.registrationuser.RegistrationUserResult
import com.example.gmail.domain.registration.registrationuser.RegistrationUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationUseCase: RegistrationUserUseCase) :ViewModel() {
    private val _registrationData: Channel<RegistrationCommand> = Channel()
    val registrationCommand = _registrationData.receiveAsFlow()

    init {
        viewModelScope.launch {
            _registrationData.send(RegistrationCommand.Idle)
        }
    }

    fun registrationAttempt(phoneNumber:String,username: String) {
        viewModelScope.launch {
            _registrationData.send(RegistrationCommand.ShowLoading)
            val command = when (registrationUseCase.registration(phoneNumber,username)) {
                RegistrationUserResult.ERROR_EMPTY_INPUT -> RegistrationCommand.ShowEmptyInputError
                RegistrationUserResult.SUCCESS -> RegistrationCommand.NavigateToHome
            }
            _registrationData.send(command)
        }
    }


    sealed class RegistrationCommand {
        object Idle : RegistrationCommand()
        object ShowLoading : RegistrationCommand()
        object ShowEmptyInputError : RegistrationCommand()
        object NavigateToHome : RegistrationCommand()
    }
}