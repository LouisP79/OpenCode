package com.quickstore.data

import android.util.Base64
import com.quickstore.BuildConfig

object RestConstant {
    const val BEARER = "Bearer "
    private const val BASIC = "Basic "
    private const val FORMAT = "%s:%s"

    const val ALT = "?alt=media"
    const val ONBOARDING = "onBoarding%2F"
    const val PRODUCT = "product%2F"
    const val CATEGORY = "category%2F"

    const val ENDPOINT_ON_BOARDING = "/onboarding"
    const val ENDPOINT_COUNTRY_LIST = "/countrylist"

    const val ENDPOINT_CART = "/cart"
    const val ENDPOINT_CART_DECREASE = "/decrease"

    const val ENDPOINT_CATEGORY_LIST = "/categorylist"

    const val ENDPOINT_TOKEN = "/token"
    const val ENDPOINT_REFRESH_TOKEN = "/refreshtoken"

    const val ENDPOINT_UPDATE_USER_INFO = "/updateuserinfo"
    const val ENDPOINT_USER_INFO = "/userinfo"
    const val ENDPOINT_REGISTER_USER = "/registeruser"
    const val ENDPOINT_USER_CHANGE_PASS = "/changepass"
    const val ENDPOINT_USER_RECOVER_PASS = "/recoverpassword"

    const val ENDPOINT_CREATE_ADDRESS = "/createaddress"
    const val ENDPOINT_ADDRESS_LIST = "/addresslist"
    const val ENDPOINT_DELETE_ADDRESS = "/deleteaddress"

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

