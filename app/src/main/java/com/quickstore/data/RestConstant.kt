package com.quickstore.data

import android.util.Base64
import com.quickstore.BuildConfig

object RestConstant {

    const val PASSWORD = "password"
    const val REFRESH_TOKEN = "refresh_token"
    const val BEARER = "Bearer "
    private const val BASIC = "Basic "
    private const val FORMAT = "%s:%s"

    const val ALT = "?alt=media"
    const val ONBOARDING = "onBoarding%2F"
    const val PRODUCT = "product%2F"
    const val CATEGORY = "category%2F"

    const val ENDPOINT_ON_BOARDING = "/onboarding"

    const val ENDPOINT_CART = "/cart"
    const val ENDPOINT_CART_DECREASE = "/decrease"

    const val ENDPOINT_CTEGORY = "/category"

    const val ENDPOINT_TOKEN = "/oauth/token"
    const val ENDPOINT_USER = "/user"
    const val ENDPOINT_USER_INFO = "/me"
    const val ENDPOINT_USER_CHANGE_PASS = "/changepass"
    const val ENDPOINT_USER_PUBLIC = "/user"
    const val ENDPOINT_USER_RECOVER_PASS = "/recoverpass"

    const val ENDPOINT_ADDRESS = "/address"
    const val ENDPOINT_DISTRICT = "/district"

    const val ENDPOINT_FIREBASE_TOKEN = "/user_token"

    const val ENDPOINT_PRODUCT = "/product"

    class Credentials {
        var authCredentials: String = BASIC + Base64.encodeToString(
                String.format(FORMAT,
                        BuildConfig.CLIENT_ID,
                        BuildConfig.CLIENT_SECRET).toByteArray(),
                Base64.NO_WRAP)
    }

}

