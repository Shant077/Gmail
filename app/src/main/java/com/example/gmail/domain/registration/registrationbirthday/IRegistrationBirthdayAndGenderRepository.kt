package com.example.gmail.domain.registration.registrationbirthday

import android.content.Context
import com.example.gmail.domain.registration.registrationuser.User

interface IRegistrationBirthdayAndGenderRepository {
    suspend fun getData(
        birthdayAndGender:BirthdayAndGender
    )
}