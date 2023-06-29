package com.example.gmail.ui.registration.birthdayandgender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmail.domain.registration.registrationbirthday.RegistrationBirthdayAndGenderResult
import com.example.gmail.domain.registration.registrationbirthday.RegistrationBirthdayAndGenderUseCase
import com.example.gmail.domain.registration.registrationuser.RegistrationUserResult
import com.example.gmail.ui.registration.registrationuser.RegistrationViewModel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BirthdayAndGenderViewModel(private val registrationBirthdayAndGenderUseCase: RegistrationBirthdayAndGenderUseCase):ViewModel() {

    private val _registrationData: Channel<RegistrationBirthdayAndGenderCommand > = Channel()
    val registrationCommand = _registrationData.receiveAsFlow()

    init {
        viewModelScope.launch {
            _registrationData.send(RegistrationBirthdayAndGenderCommand.Idle)
        }
    }


    fun registrationAttempt(dataMonth:String,dataDate:String,dataYear:String,gender:String) {
        viewModelScope.launch {
            _registrationData.send(RegistrationBirthdayAndGenderCommand.ShowLoading)
            val command = when (registrationBirthdayAndGenderUseCase.registration(dataMonth,dataDate,dataYear,gender)) {
                RegistrationBirthdayAndGenderResult.ERROR_EMPTY_INPUT -> RegistrationBirthdayAndGenderCommand.ShowEmptyInputError
                RegistrationBirthdayAndGenderResult.SUCCESS-> RegistrationBirthdayAndGenderCommand.NavigateToHome
            }
            _registrationData.send(command)
        }
    }

    sealed class RegistrationBirthdayAndGenderCommand {
        object Idle : RegistrationBirthdayAndGenderCommand()
        object ShowLoading : RegistrationBirthdayAndGenderCommand()
        object ShowEmptyInputError : RegistrationBirthdayAndGenderCommand()
        object NavigateToHome : RegistrationBirthdayAndGenderCommand()
    }
}