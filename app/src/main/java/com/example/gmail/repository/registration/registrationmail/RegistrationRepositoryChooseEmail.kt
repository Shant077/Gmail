package com.example.gmail.repository.registration.registrationmail

import android.content.Context
import com.example.gmail.domain.registration.registrationchooseemail.Email
import com.example.gmail.domain.registration.registrationchooseemail.IRegistrationChooseEmail

class RegistrationRepositoryChooseEmail(private val context: Context):IRegistrationChooseEmail {
    override suspend fun getData(email: Email) {
        val sharedPreference=context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("email", email.email)
        editor.putLong("l", 100L)
        editor.commit()
    }
}