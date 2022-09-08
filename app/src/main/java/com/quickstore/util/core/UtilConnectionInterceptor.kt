package com.quickstore.util.core

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.quickstore.R
import java.io.IOException

class UtilConnectionInterceptor constructor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnectionOn()) {
            throw NoConnectivityException(
                context
            )
        }else {
            chain.proceed(chain.request())
        }
    }

    private fun isConnectionOn(): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }

        return result
    }

    class NoConnectivityException(val context: Context) : IOException() {
        override val message: String
            get() = context.getString(R.string.error_conection)
    }
}
