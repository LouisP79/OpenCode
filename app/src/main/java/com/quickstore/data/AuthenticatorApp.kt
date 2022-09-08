package com.quickstore.data

import android.content.Context
import android.os.Build
import com.quickstore.BuildConfig
import com.quickstore.R
import com.quickstore.data.token.TokenWebServices
import com.quickstore.preferences.ApplicationPreferences
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

/**
 *Created by sud on 21/10/19 for NotPOS
 *
 * Clase que maneja la reuatentificacion de los requests de manera automatica cuando expira el token.
 *
 * @author Anton Tchistiakova
 * @version 1.1.0
 * @since 12/2019
 */
class AuthenticatorApp(private val applicationPreferences: ApplicationPreferences, private val context: Context) : Authenticator {

    /**
     * Servicio de autentifacion.
     */
    private val oAuthService = createService()

    /**
     * Este servicio se ejecuta cada vez que un request devuelve error 401
     */
    override fun authenticate(route: Route?, response: Response): Request? {

        /**
         * Si no exite token de autentificacion no hay como reautentificar al usuario
         */
        val refreshToken = applicationPreferences.token?.refreshToken ?: return null
        /**
         * Solo se ejecutan 2 intentos de reautentificar al usuario.
         */
        if (responseCount(response) > 2) return null

        val refreshReponse = oAuthService.refreshToken(RestConstant.Credentials().authCredentials,
                refreshToken,
                RestConstant.REFRESH_TOKEN).execute()

        return when {
            refreshReponse.isSuccessful -> {
                val token = refreshReponse.body()!!
                applicationPreferences.token = token

                response.request.newBuilder()
                    .header("Authorization", token.accessToken)
                    .build()
            }
            refreshReponse.code() == HttpURLConnection.HTTP_BAD_REQUEST -> {
                throw RefreshTokenException(context)
            }
            else -> throw UnknownException(context)
        }
    }

    /**
     * Se crea el servicio de autentificacion de manera manual para evitar un buckle infinito al usar
     * Koin para inyectar la dependencia.
     */
    private fun createService(): TokenWebServices {
        val interceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor {
                    it.proceed(
                            it.request().newBuilder()
                                    .header("User-Agent",
                                            "Android ${Build.VERSION.RELEASE}; ${Build.MODEL}")
                                    .build()
                    )
                }
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BASE)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

        return retrofit.create(TokenWebServices::class.java)
    }

    /**
     * Cuenta la cantidad de veces que se ejecuto el servicio de autentificacion.
     */
    private fun responseCount(response: Response): Int {
        var r = response
        var result = 1
        while (r.priorResponse != null) {
            result++
            r = response.priorResponse ?: return result
        }
        return result
    }

    class RefreshTokenException(val context: Context) : IOException() {
        override val message: String
            get() = context.getString(R.string.error_refresh_token)
    }

    class UnknownException(val context: Context) : IOException() {
        override val message: String
            get() = context.getString(R.string.error_unknow)
    }
}