package com.example.gmail.domain.passwordsignin

import com.example.gmail.domain.login.LoginResult

class PasswordSignInUseCase (private val passwordSignInRepository:IPasswordSignInRepository){

    suspend fun password(password: String): PasswordSignInResult {
        if (password == "") {
            return PasswordSignInResult.ERROR_EMPTY_INPUT
        }
        return when (passwordSignInRepository.password(password)) {
            true -> PasswordSignInResult.SUCCESS
            false -> PasswordSignInResult.ERROR_WRONG_CREDENTIALS
        }
    }
}