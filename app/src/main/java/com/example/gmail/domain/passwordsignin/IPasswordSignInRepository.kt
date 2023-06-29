package com.example.gmail.domain.passwordsignin

interface IPasswordSignInRepository {
    suspend fun password(password: String): Boolean
}
