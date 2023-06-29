package com.example.gmail.repository.passworsignin

import android.content.Context
import com.example.gmail.domain.passwordsignin.IPasswordSignInRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PasswordSignInRepository(private val context: Context) :IPasswordSignInRepository {
    override suspend fun password(password: String) = withContext(Dispatchers.IO) {
        delay(3000)
        return@withContext password == getPassword()
    }

    private fun getPassword(): String{
        val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        return sharedPreference.getString("password", "default_value")!!
    }
}