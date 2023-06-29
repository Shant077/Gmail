package com.example.gmail.ui.passwordforsignin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmail.domain.login.LoginResult
import com.example.gmail.domain.passwordsignin.PasswordSignInResult
import com.example.gmail.domain.passwordsignin.PasswordSignInUseCase
import com.example.gmail.ui.login.LoginViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PasswordSignInViewModel(private val passwordSignInUseCase: PasswordSignInUseCase) :ViewModel() {
    private val _loginData: Channel<PasswordSignInCommand> = Channel()
    val loginCommand = _loginData.receiveAsFlow()


    init {
        viewModelScope.launch {
            _loginData.send(PasswordSignInCommand.Idle)
        }
    }


    fun passwordAttempt(password: String) {
        viewModelScope.launch {
            _loginData.send(PasswordSignInCommand.ShowLoading)
            val command = when (passwordSignInUseCase.password(password)) {
                PasswordSignInResult.ERROR_WRONG_CREDENTIALS -> PasswordSignInCommand.ShowWrongCredentialsError
                PasswordSignInResult.ERROR_EMPTY_INPUT -> PasswordSignInCommand.ShowEmptyInputError
                PasswordSignInResult.SUCCESS -> PasswordSignInCommand.NavigateToHome
            }
            _loginData.send(command)
        }
    }

    sealed class PasswordSignInCommand {
        object Idle: PasswordSignInCommand()
        object ShowLoading: PasswordSignInCommand()
        object ShowEmptyInputError: PasswordSignInCommand()
        object ShowWrongCredentialsError: PasswordSignInCommand()
        object NavigateToHome: PasswordSignInCommand()
    }
}