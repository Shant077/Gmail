package com.example.gmail.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmail.domain.login.LoginResult
import com.example.gmail.domain.login.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel(){
    private val _loginData: Channel<LoginCommand> = Channel()
    val loginCommand = _loginData.receiveAsFlow()

    init {
        viewModelScope.launch {
            _loginData.send(LoginCommand.Idle)
        }
    }

    fun loginAttempt(email: String) {
        viewModelScope.launch {
            _loginData.send(LoginCommand.ShowLoading)
            val command = when (loginUseCase.login(email)) {
                LoginResult.ERROR_WRONG_CREDENTIALS -> LoginCommand.ShowWrongCredentialsError
                LoginResult.ERROR_EMPTY_INPUT -> LoginCommand.ShowEmptyInputError
                LoginResult.SUCCESS -> LoginCommand.NavigateToHome
            }
            _loginData.send(command)
        }
    }


    sealed class LoginCommand {
        object Idle: LoginCommand()
        object ShowLoading: LoginCommand()
        object ShowEmptyInputError: LoginCommand()
        object ShowWrongCredentialsError: LoginCommand()
        object NavigateToHome: LoginCommand()
    }
}