package com.example.gmail.domain.registration.registrationuser

interface IRegistrationRepositoryUser {
    suspend fun getData(
       user: User
    )
}