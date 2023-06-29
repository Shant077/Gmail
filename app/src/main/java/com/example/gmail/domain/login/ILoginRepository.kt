package com.example.gmail.domain.login

interface ILoginRepository {
    suspend fun login(email: String): Boolean
}