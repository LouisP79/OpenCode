package com.quickstore.di

import com.quickstore.ui.useCase.login.viewModel.LoginViewModel
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.ui.useCase.onboarding.viewModel.OnBoardingViewModel
import com.quickstore.ui.useCase.recoverPassword.viewModel.RecoverPasswordViewModel
import com.quickstore.ui.useCase.register.viewModel.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { OnBoardingViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { RecoverPasswordViewModel(get()) }
}