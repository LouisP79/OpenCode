package com.quickstore.di

import com.quickstore.data.address.AddressWebServices
import com.quickstore.data.cart.CartWebServices
import com.quickstore.data.category.CategoryWebServices
import com.quickstore.data.district.DistrictWebServices
import com.quickstore.data.onBoarding.OnBoardingWebServices
import com.quickstore.data.product.ProductWebServices
import com.quickstore.data.token.TokenWebServices
import com.quickstore.data.user.UserWebServices
import com.quickstore.ui.useCase.login.repository.LoginRepository
import com.quickstore.ui.useCase.main.repository.MainRepository
import com.quickstore.ui.useCase.onboarding.repository.OnBoardingRepository
import com.quickstore.ui.useCase.recoverPassword.repository.RecoverPasswordRepository
import com.quickstore.ui.useCase.register.repository.RegisterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideOnBoardingRepository(get()) }
    single { provideRegisterRepository(get(), get()) }
    single { provideLoginRepository(get(), get()) }
    single { provideMainRepository(get(), get(), get(), get(), get(), get()) }
    single { provideRecoverPasswordRepository(get()) }
}

fun provideOnBoardingRepository(onBoardingWebServices: OnBoardingWebServices): OnBoardingRepository = OnBoardingRepository(onBoardingWebServices)
fun provideRegisterRepository(userWebServices: UserWebServices, tokenWebServices: TokenWebServices): RegisterRepository = RegisterRepository(userWebServices, tokenWebServices)
fun provideLoginRepository(tokenWebServices: TokenWebServices, userWebServices: UserWebServices): LoginRepository = LoginRepository(tokenWebServices, userWebServices)
fun provideMainRepository(productWebServices: ProductWebServices, cartWebServices: CartWebServices, categoryWebServices: CategoryWebServices, userWebServices: UserWebServices, addressWebServices: AddressWebServices, districtWebServices: DistrictWebServices): MainRepository = MainRepository(productWebServices, cartWebServices, categoryWebServices, userWebServices, addressWebServices, districtWebServices)
fun provideRecoverPasswordRepository(userWebServices: UserWebServices): RecoverPasswordRepository = RecoverPasswordRepository(userWebServices)
