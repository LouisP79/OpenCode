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

    private const val V1_API = "/api/v1"
    private const val PUBLIC_API = "$V1_API/public"
    private const val SECURED_API = "$V1_API/secured"

    const val ENDPOINT_ON_BOARDING = "$PUBLIC_API/slider/all"

    const val ENDPOINT_CART = "$SECURED_API/cart"
    const val ENDPOINT_CART_DECREASE = "$ENDPOINT_CART/decrease"

    const val ENDPOINT_CTEGORY = "$PUBLIC_API/category"

    const val ENDPOINT_TOKEN = "/oauth/token"
    const val ENDPOINT_USER = "$SECURED_API/user"
    const val ENDPOINT_USER_INFO = "$ENDPOINT_USER/me"
    const val ENDPOINT_USER_CHANGE_PASS = "$ENDPOINT_USER/changepass"
    const val ENDPOINT_USER_PUBLIC = "$PUBLIC_API/user"
    const val ENDPOINT_USER_RECOVER_PASS = "$ENDPOINT_USER_PUBLIC/recoverpass"

    const val ENDPOINT_ADDRESS = "$SECURED_API/address"
    const val ENDPOINT_DISTRICT = "$PUBLIC_API/district"

    const val ENDPOINT_FIREBASE_TOKEN = "$SECURED_API/user_token"

    const val ENDPOINT_PRODUCT = "$PUBLIC_API/product"

    class Credentials {
        var authCredentials: String = BASIC + Base64.encodeToString(
                String.format(FORMAT,
                        BuildConfig.CLIENT_ID,
                        BuildConfig.CLIENT_SECRET).toByteArray(),
                Base64.NO_WRAP)
    }

}

