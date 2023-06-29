package com.example.gmail.domain.registration.registrationphonenumber

import com.example.gmail.domain.registration.registrationuser.RegistrationUserResult
import com.example.gmail.domain.registration.registrationuser.User

class PhoneNumberUseCase(private val iPhoneNumberRepository: IPhoneNumberRepository){
    suspend fun registration(
      phoneNumber: String
    ): PhoneNumberResult {
        if (phoneNumber == "") {
            return PhoneNumberResult.ERROR_EMPTY_INPUT
        }
        val phoneNumber = PhoneNumber(phoneNumber)
        iPhoneNumberRepository.getData(phoneNumber)
        return PhoneNumberResult.SUCCESS
    }
}
