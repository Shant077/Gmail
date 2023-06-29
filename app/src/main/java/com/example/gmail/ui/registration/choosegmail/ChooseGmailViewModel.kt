package com.example.gmail.ui.registration.choosegmail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmail.domain.registration.registrationchooseemail.RegistrationChooseEmailResult
import com.example.gmail.domain.registration.registrationchooseemail.RegistrationChooseEmailUseCase
import com.example.gmail.domain.registration.registrationuser.RegistrationUserResult
import com.example.gmail.ui.registration.registrationuser.RegistrationViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChooseGmailViewModel(private val chooseEmailUseCase: RegistrationChooseEmailUseCase) :ViewModel() {
    private val _registrationData: Channel<RegistrationChooseEmailCommand> = Channel()
    val registrationCommand = _registrationData.receiveAsFlow()

    init {
        viewModelScope.launch {
            _registrationData.send(RegistrationChooseEmailCommand.Idle)
        }
    }

    fun registrationAttempt(email:String) {
        viewModelScope.launch {
            _registrationData.send(RegistrationChooseEmailCommand.ShowLoading)
            val command = when (chooseEmailUseCase.registration(email)) {
                RegistrationChooseEmailResult.ERROR_EMPTY_INPUT ->RegistrationChooseEmailCommand.ShowEmptyInputError
                RegistrationChooseEmailResult.SUCCESS -> RegistrationChooseEmailCommand.NavigateToHome
            }
            _registrationData.send(command)
        }
    }

    sealed class RegistrationChooseEmailCommand {
        object Idle : RegistrationChooseEmailCommand()
        object ShowLoading : RegistrationChooseEmailCommand()
        object ShowEmptyInputError : RegistrationChooseEmailCommand()
        object NavigateToHome : RegistrationChooseEmailCommand()
    }
}