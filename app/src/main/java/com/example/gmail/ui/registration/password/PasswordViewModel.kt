package com.example.gmail.ui.registration.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmail.domain.registration.registrationpassword.RegistrationPasswordResult
import com.example.gmail.domain.registration.registrationpassword.RegistrationPasswordUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PasswordViewModel(private val passwordUseCase: RegistrationPasswordUseCase) :ViewModel() {
    private val _registrationData: Channel<RegistrationPasswordCommand> = Channel()
    val registrationCommand = _registrationData.receiveAsFlow()

    init {
        viewModelScope.launch {
            _registrationData.send(RegistrationPasswordCommand.Idle)
        }
    }
    fun registrationAttempt( password: String) {
        viewModelScope.launch {
            _registrationData.send(RegistrationPasswordCommand.ShowLoading)
            val command = when (passwordUseCase.registration(password)) {
                RegistrationPasswordResult.ERROR_EMPTY_INPUT -> RegistrationPasswordCommand.ShowEmptyInputError
                RegistrationPasswordResult.ERROR_LENGTH_INPUT->RegistrationPasswordCommand.LengthInputError
                RegistrationPasswordResult.SUCCESS -> RegistrationPasswordCommand.NavigateToHome
            }
            _registrationData.send(command)
        }
    }

    sealed class RegistrationPasswordCommand {
        object Idle : RegistrationPasswordCommand()
        object ShowLoading : RegistrationPasswordCommand()
        object ShowEmptyInputError : RegistrationPasswordCommand()
        object LengthInputError:RegistrationPasswordCommand()
        object NavigateToHome : RegistrationPasswordCommand()
    }
}

