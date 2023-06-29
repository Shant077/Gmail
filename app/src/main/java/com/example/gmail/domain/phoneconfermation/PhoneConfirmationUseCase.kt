package com.example.gmail.domain.phoneconfermation

class PhoneConfirmationUseCase(private val iPhoneConfirmationRepository: IPhoneConfirmationRepository) {
    suspend fun checkPinCode(pinCode: String): PhoneConfirmationResult {
        return when (iPhoneConfirmationRepository.checkPinCode(pinCode)) {
            true -> PhoneConfirmationResult.SUCCESS
            false -> PhoneConfirmationResult.ERROR_WRONG_CREDENTIALS
        }
    }
}