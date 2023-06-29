package com.example.gmail.domain.phoneconfermation

interface IPhoneConfirmationRepository {
    suspend fun checkPinCode(pinCode:String):Boolean
}