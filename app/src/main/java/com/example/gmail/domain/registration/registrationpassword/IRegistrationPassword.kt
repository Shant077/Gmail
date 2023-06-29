package com.example.gmail.domain.registration.registrationpassword

import com.example.gmail.domain.registration.registrationchooseemail.Email

interface IRegistrationPassword {
    suspend fun getData(
      password:Password
    )
}