package com.example.gmail.domain.registration.registrationbirthday



class RegistrationBirthdayAndGenderUseCase(private val registrationRepositoryBirthday: IRegistrationBirthdayAndGenderRepository) {
    suspend fun registration(
        monthDate: String,
        dayDate: String,
        yearDate:String,
        gender:String
    ): RegistrationBirthdayAndGenderResult {
        if (monthDate == "" || dayDate == "" || yearDate == "" || gender == "") {
            return RegistrationBirthdayAndGenderResult.ERROR_EMPTY_INPUT
        }
        val birthdayAndGender=BirthdayAndGender(monthDate,dayDate,yearDate,gender)
        registrationRepositoryBirthday.getData(birthdayAndGender)
        return RegistrationBirthdayAndGenderResult.SUCCESS
    }

}