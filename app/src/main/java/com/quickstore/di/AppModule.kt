package com.quickstore.di

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.quickstore.BuildConfig
import com.quickstore.data.AuthenticatorApp
import com.quickstore.preferences.ApplicationPreferences
import com.quickstore.util.core.UtilConnectionInterceptor

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { provideGson() }
    single { provideKeyGenParameterSpec() }
    single { provideMasterKey(androidContext(), get()) }
    single { provideAppPreference(androidContext(), get(), get()) }
    single { provideJacksonConverterFactory() }
    single { provideHttpInterceptor() }
    single { provideOkHttpClient(get(), get(), get()) }
    single { provideRxJava2CallAdapterFactory() }
    single { provideRetrofit(get(), get(), get()) }
    single { AuthenticatorApp(get(), androidContext()) }
    single { UtilConnectionInterceptor(androidContext()) }
}

//GSON
fun provideGson(): Gson {
    return Gson()
}

//PREFERENCES
//·····································
fun provideKeyGenParameterSpec(): KeyGenParameterSpec {
    return KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(256)
        .build()
}

fun provideMasterKey(context: Context, spec: KeyGenParameterSpec): MasterKey {
    return MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()
}

fun provideAppPreference(context: Context, gson: Gson, masterKey: MasterKey): ApplicationPreferences {
    return ApplicationPreferences(context, gson, masterKey)
}
//PREFERENCES
//·····································

//RETROFIT
//·····································
fun provideJacksonConverterFactory(): JacksonConverterFactory {
    return JacksonConverterFactory.create()
}

fun provideHttpInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, authenticator: AuthenticatorApp, connectionInterceptor: UtilConnectionInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(connectionInterceptor)
            .authenticator(authenticator)
            .addInterceptor {
                it.proceed(
                        it.request().newBuilder()
                                .header("User-Agent",
                                        "Android ${Build.VERSION.RELEASE}; ${Build.MODEL}")
                                .build()
                )
            }
            .build()
}

fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
    return RxJava2CallAdapterFactory.create()
}

fun provideRetrofit(okHttpClient: OkHttpClient, jacksonConverterFactory: JacksonConverterFactory, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit {
    return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .client(okHttpClient)
            .addConverterFactory(jacksonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()
}
//RETROFIT
//·····································
