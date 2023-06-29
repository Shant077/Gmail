package com.example.gmail.ui.registration.phonenumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmail.domain.registration.registrationphonenumber.PhoneNumberResult
import com.example.gmail.domain.registration.registrationphonenumber.PhoneNumberUseCase
import com.example.gmail.domain.registration.registrationuser.RegistrationUserResult
import com.example.gmail.ui.registration.registrationuser.RegistrationViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PhoneNumberViewModel (private val phoneNumberUseCase: PhoneNumberUseCase):ViewModel() {

    private val _registrationData: Channel<RegistrationPhoneNumberCommand> = Channel()
    val registrationCommand = _registrationData.receiveAsFlow()

    init {
        viewModelScope.launch {
            _registrationData.send(RegistrationPhoneNumberCommand.Idle)
        }
    }

    fun registrationAttempt(phoneNumber:String) {
        viewModelScope.launch {
            _registrationData.send(RegistrationPhoneNumberCommand.ShowLoading)
            val command = when (phoneNumberUseCase.registration(phoneNumber)) {
                PhoneNumberResult.ERROR_EMPTY_INPUT -> RegistrationPhoneNumberCommand.ShowEmptyInputError
                PhoneNumberResult.SUCCESS -> RegistrationPhoneNumberCommand.NavigateToHome
            }
            _registrationData.send(command)
        }
    }

    sealed class RegistrationPhoneNumberCommand {
        object Idle : RegistrationPhoneNumberCommand()
        object ShowLoading : RegistrationPhoneNumberCommand()
        object ShowEmptyInputError : RegistrationPhoneNumberCommand()
        object NavigateToHome : RegistrationPhoneNumberCommand()
    }
}