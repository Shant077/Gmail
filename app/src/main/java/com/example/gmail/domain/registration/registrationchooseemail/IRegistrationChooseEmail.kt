package com.example.gmail.domain.registration.registrationchooseemail

import com.example.gmail.domain.registration.registrationbirthday.BirthdayAndGender

interface IRegistrationChooseEmail {
    suspend fun getData(
        email: Email
    )
}