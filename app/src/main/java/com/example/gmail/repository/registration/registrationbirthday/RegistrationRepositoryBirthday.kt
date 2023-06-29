package com.example.gmail.repository.registration.registrationbirthday

import android.annotation.SuppressLint
import android.content.Context
import com.example.gmail.domain.registration.registrationbirthday.BirthdayAndGender
import com.example.gmail.domain.registration.registrationbirthday.IRegistrationBirthdayAndGenderRepository

class RegistrationRepositoryBirthday (private val context: Context):IRegistrationBirthdayAndGenderRepository{
    @SuppressLint("ApplySharedPref")
    override suspend fun getData(birthdayAndGender: BirthdayAndGender) {
        val sharedPreference=context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("month", birthdayAndGender.month)
        editor.putString("date", birthdayAndGender.date)
        editor.putString("year", birthdayAndGender.year)
        editor.putString("gender", birthdayAndGender.gender)
        editor.putLong("l", 100L)
        editor.commit()

    }
}