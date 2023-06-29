package com.example.gmail.domain.registration.registrationchooseemail

import com.example.gmail.domain.registration.registrationuser.RegistrationUserResult
import com.example.gmail.domain.registration.registrationuser.User
import com.example.gmail.repository.registration.registrationmail.RegistrationRepositoryChooseEmail


class RegistrationChooseEmailUseCase(private val  registrationRepositoryChooseEmail: IRegistrationChooseEmail) {
    suspend fun registration(
        email: String,
    ): RegistrationChooseEmailResult {
        if (email == "") {
            return RegistrationChooseEmailResult.ERROR_EMPTY_INPUT
        }
        val email = Email(email)
        registrationRepositoryChooseEmail.getData(email)
        return RegistrationChooseEmailResult.SUCCESS
    }
}