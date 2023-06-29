package com.example.gmail.repository.login

import android.content.Context
import com.example.gmail.domain.login.ILoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginRepository(private val context: Context) : ILoginRepository {
    override suspend fun login(email: String) = withContext(Dispatchers.IO) {
        delay(3000)
        return@withContext email == getEmail()
    }
    private fun getEmail(): String {
        val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        return sharedPreference.getString("email", "default_value")!!
    }
}
