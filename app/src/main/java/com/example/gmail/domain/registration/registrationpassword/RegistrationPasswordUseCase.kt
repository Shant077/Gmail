package com.example.gmail.domain.registration.registrationpassword

class RegistrationPasswordUseCase(private val registrationPassword: IRegistrationPassword) {
    suspend fun registration(
        password: String,
    ): RegistrationPasswordResult {
        if (password == "") {
            return RegistrationPasswordResult.ERROR_EMPTY_INPUT
        }
        if (password.length < 6) {
            return RegistrationPasswordResult.ERROR_LENGTH_INPUT
        }
        val password = Password(password)
        registrationPassword.getData(password)
        return RegistrationPasswordResult.SUCCESS
    }
}