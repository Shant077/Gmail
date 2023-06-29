package com.example.gmail.domain.registration.registrationuser

class RegistrationUserUseCase(private val registrationRepositoryUser: IRegistrationRepositoryUser) {

    suspend fun registration(
        firstName: String,
        lastName: String,
    ): RegistrationUserResult {
        if (firstName == "" || lastName == "") {
            return RegistrationUserResult.ERROR_EMPTY_INPUT
        }
        val user = User(firstName, lastName)
        registrationRepositoryUser.getData(user)
        return RegistrationUserResult.SUCCESS
    }
}