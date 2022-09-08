package com.quickstore.di

import com.quickstore.data.address.AddressWebServices
import com.quickstore.data.cart.CartWebServices
import com.quickstore.data.category.CategoryWebServices
import com.quickstore.data.district.DistrictWebServices
import com.quickstore.data.firebaseToken.FirebaseTokenWebServices
import com.quickstore.data.onBoarding.OnBoardingWebServices
import com.quickstore.data.product.ProductWebServices
import com.quickstore.data.token.TokenWebServices
import com.quickstore.data.user.UserWebServices
import org.koin.dsl.module
import retrofit2.Retrofit

val webServiceModule = module {
    single { provideTokenServices(get()) }
    single { provideUserServices(get()) }
    single { provideOnBoardingServices(get()) }
    single { provideProductServices(get()) }
    single { provideCartServices(get()) }
    single { provideCategoryServices(get()) }
    single { provideDistrictServices(get()) }
    single { provideAddressServices(get()) }
    single { provideFirebaseTokenServices(get()) }
}

fun provideTokenServices(retrofit: Retrofit): TokenWebServices = retrofit.create(TokenWebServices::class.java)
fun provideUserServices(retrofit: Retrofit): UserWebServices = retrofit.create(UserWebServices::class.java)
fun provideOnBoardingServices(retrofit: Retrofit): OnBoardingWebServices = retrofit.create(OnBoardingWebServices::class.java)
fun provideProductServices(retrofit: Retrofit): ProductWebServices = retrofit.create(ProductWebServices::class.java)
fun provideCartServices(retrofit: Retrofit): CartWebServices = retrofit.create(CartWebServices::class.java)
fun provideCategoryServices(retrofit: Retrofit): CategoryWebServices = retrofit.create(CategoryWebServices::class.java)
fun provideDistrictServices(retrofit: Retrofit): DistrictWebServices = retrofit.create(DistrictWebServices::class.java)
fun provideAddressServices(retrofit: Retrofit): AddressWebServices = retrofit.create(AddressWebServices::class.java)
fun provideFirebaseTokenServices(retrofit: Retrofit): FirebaseTokenWebServices = retrofit.create(FirebaseTokenWebServices::class.java)

