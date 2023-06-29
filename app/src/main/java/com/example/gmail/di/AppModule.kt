package com.example.gmail.di

import com.example.gmail.domain.login.ILoginRepository
import com.example.gmail.domain.login.LoginUseCase
import com.example.gmail.domain.passwordsignin.IPasswordSignInRepository
import com.example.gmail.domain.passwordsignin.PasswordSignInUseCase
import com.example.gmail.domain.phoneconfermation.IPhoneConfirmationRepository
import com.example.gmail.domain.phoneconfermation.PhoneConfirmationUseCase
import com.example.gmail.domain.registration.registrationbirthday.IRegistrationBirthdayAndGenderRepository
import com.example.gmail.domain.registration.registrationbirthday.RegistrationBirthdayAndGenderUseCase
import com.example.gmail.domain.registration.registrationchooseemail.IRegistrationChooseEmail
import com.example.gmail.domain.registration.registrationchooseemail.RegistrationChooseEmailUseCase
import com.example.gmail.domain.registration.registrationpassword.IRegistrationPassword
import com.example.gmail.domain.registration.registrationpassword.RegistrationPasswordUseCase
import com.example.gmail.domain.registration.registrationphonenumber.IPhoneNumberRepository
import com.example.gmail.domain.registration.registrationphonenumber.PhoneNumberUseCase
import com.example.gmail.domain.registration.registrationuser.IRegistrationRepositoryUser
import com.example.gmail.domain.registration.registrationuser.RegistrationUserUseCase
import com.example.gmail.repository.login.LoginRepository
import com.example.gmail.repository.passworsignin.PasswordSignInRepository
import com.example.gmail.repository.phoneconfirmation.PhoneConfirmationRepository
import com.example.gmail.repository.registration.registrationbirthday.RegistrationRepositoryBirthday
import com.example.gmail.repository.registration.registrationmail.RegistrationRepositoryChooseEmail
import com.example.gmail.repository.registration.registrationpassword.RegistrationRepositoryPassword
import com.example.gmail.repository.registration.registrationphonenumber.RegistrationRepositoryPhoneNumber
import com.example.gmail.repository.registration.registrationuser.RegistrationRepositoryUser
import com.example.gmail.ui.login.LoginViewModel
import com.example.gmail.ui.passwordforsignin.PasswordSignInViewModel
import com.example.gmail.ui.phoneconfermation.PhoneConfirmationViewModel
import com.example.gmail.ui.registration.birthdayandgender.BirthdayAndGenderViewModel
import com.example.gmail.ui.registration.choosegmail.ChooseGmailViewModel
import com.example.gmail.ui.registration.password.PasswordViewModel
import com.example.gmail.ui.registration.phonenumber.PhoneNumberViewModel
import com.example.gmail.ui.registration.registrationuser.RegistrationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<IRegistrationRepositoryUser> { RegistrationRepositoryUser(androidContext()) }
    factory { RegistrationUserUseCase(get()) }


    factory<IRegistrationBirthdayAndGenderRepository> { RegistrationRepositoryBirthday(androidContext()) }
    factory { RegistrationBirthdayAndGenderUseCase(get()) }

    factory<IRegistrationChooseEmail> { RegistrationRepositoryChooseEmail(androidContext()) }
    factory { RegistrationChooseEmailUseCase(get()) }

    factory<IRegistrationPassword> { RegistrationRepositoryPassword(androidContext()) }
    factory { RegistrationPasswordUseCase(get())}

    factory<IPhoneNumberRepository> { RegistrationRepositoryPhoneNumber(androidContext()) }
    factory { PhoneNumberUseCase(get())}

    factory<IPhoneConfirmationRepository> { PhoneConfirmationRepository() }
    factory { PhoneConfirmationUseCase(get()) }

    factory<ILoginRepository> { LoginRepository(androidContext()) }
    factory { LoginUseCase(get()) }

    factory<IPasswordSignInRepository> { PasswordSignInRepository(androidContext()) }
    factory { PasswordSignInUseCase(get()) }

    viewModel { RegistrationViewModel(get()) }
    viewModel { BirthdayAndGenderViewModel(get()) }
    viewModel { ChooseGmailViewModel(get()) }
    viewModel { PasswordViewModel(get()) }
    viewModel { PhoneNumberViewModel(get()) }
    viewModel { PhoneConfirmationViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { PasswordSignInViewModel(get()) }

}
