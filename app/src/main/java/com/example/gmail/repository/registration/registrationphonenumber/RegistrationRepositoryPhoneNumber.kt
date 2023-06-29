package com.example.gmail.repository.registration.registrationphonenumber

import android.content.Context
import com.example.gmail.domain.registration.registrationphonenumber.IPhoneNumberRepository
import com.example.gmail.domain.registration.registrationphonenumber.PhoneNumber
import kotlinx.coroutines.delay

class RegistrationRepositoryPhoneNumber(private val context: Context) : IPhoneNumberRepository {
    override suspend fun getData(phoneNumber: PhoneNumber) {
        delay(1000)
        val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("phonenumber", phoneNumber.phoneNumber)
        editor.putLong("l", 100L)
        editor.commit()
    }
}