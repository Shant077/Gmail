package com.example.gmail.repository.registration.registrationpassword

import android.content.Context
import com.example.gmail.domain.registration.registrationpassword.IRegistrationPassword
import com.example.gmail.domain.registration.registrationpassword.Password

class RegistrationRepositoryPassword(private val context: Context):IRegistrationPassword {
    override suspend fun getData(password: Password) {
        val sharedPreference=context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("password", password.password)
        editor.putLong("l", 100L)
        editor.commit()
    }

}