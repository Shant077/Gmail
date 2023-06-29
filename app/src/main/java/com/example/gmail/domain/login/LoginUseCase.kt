package com.example.gmail.domain.login

class LoginUseCase (private val loginRepository: ILoginRepository){


    suspend fun login(email: String): LoginResult {
        if (email == "") {
            return LoginResult.ERROR_EMPTY_INPUT
        }
        return when (loginRepository.login(email)) {
            true -> LoginResult.SUCCESS
            false -> LoginResult.ERROR_WRONG_CREDENTIALS
        }
    }
}