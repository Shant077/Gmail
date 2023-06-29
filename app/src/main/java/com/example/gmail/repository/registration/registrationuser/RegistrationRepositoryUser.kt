package com.example.gmail.repository.registration.registrationuser

import android.annotation.SuppressLint
import android.content.Context
import com.example.gmail.domain.registration.registrationuser.IRegistrationRepositoryUser
import com.example.gmail.domain.registration.registrationuser.User

class RegistrationRepositoryUser(private val context: Context) : IRegistrationRepositoryUser {
    @SuppressLint("ApplySharedPref")
    override suspend fun getData(user: User) {
        val sharedPreference=context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("firstName", user.firstName)
        editor.putString("lastName", user.lastName)
        editor.putLong("l", 100L)
        editor.commit()
    }

}