package com.example.gmail.domain.registration.registrationphonenumber

import com.example.gmail.domain.registration.registrationuser.User

interface IPhoneNumberRepository {
    suspend fun getData(
        phoneNumber: PhoneNumber
    )
}
