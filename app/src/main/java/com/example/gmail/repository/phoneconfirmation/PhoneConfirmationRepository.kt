package com.example.gmail.repository.phoneconfirmation

import com.example.gmail.domain.phoneconfermation.IPhoneConfirmationRepository
import com.google.android.exoplayer2.trackselection.ExoTrackSelection.Definition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PhoneConfirmationRepository : IPhoneConfirmationRepository {
    override suspend fun checkPinCode(pinCode: String) = withContext(Dispatchers.IO) {
        delay(3000)
        return@withContext pinCode == "1234"
    }
}