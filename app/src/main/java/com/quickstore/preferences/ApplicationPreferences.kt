package com.quickstore.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quickstore.data.country.model.CountryModel
import com.quickstore.data.token.model.TokenModel
import com.quickstore.data.user.model.UserModel
import java.lang.reflect.Type


class ApplicationPreferences(context: Context, private val gson: Gson, masterKey: MasterKey) {

    companion object {
        private const val NAME = "appPreference"
        private const val KEY_FIREBASE_TOKEN = "firebase_token"
        private const val KEY_TOKEN = "token"
        private const val KEY_USER = "user"
        private const val KEY_ONBOARDING = "onBoarding"
        private const val KEY_COUNTRIES = "countries"
    }

    private val eSharedPreferences: SharedPreferences
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        eSharedPreferences = EncryptedSharedPreferences.create(
            context,
            NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }

    var token: TokenModel?
        get() = gson.fromJson(eSharedPreferences.getString(KEY_TOKEN, null), TokenModel::class.java)
        set(value) = eSharedPreferences.edit{putString(KEY_TOKEN, gson.toJson(value))}

    var firebaseToken: String?
        get() = eSharedPreferences.getString(KEY_FIREBASE_TOKEN, null)
        set(value) = eSharedPreferences.edit{putString(KEY_FIREBASE_TOKEN, value)}

    var user: UserModel?
        get() = gson.fromJson(eSharedPreferences.getString(KEY_USER, null), UserModel::class.java)
        set(value) = eSharedPreferences.edit{putString(KEY_USER, gson.toJson(value))}

    var onBoarding: Boolean
        get() = sharedPreferences.getBoolean(KEY_ONBOARDING, false)
        set(visible) = sharedPreferences.edit{putBoolean(KEY_ONBOARDING, visible)}

    var countries: List<CountryModel>
        get() = gson.fromJson(sharedPreferences.getString(KEY_COUNTRIES, java.lang.String.valueOf(ArrayList<CountryModel>())), object : TypeToken<List<CountryModel>>() {}.type)
        set(value){
            val type: Type = object : TypeToken<List<CountryModel?>?>() {}.type
            sharedPreferences.edit{putString(KEY_COUNTRIES, gson.toJson(value, type))}
        }

    fun getBearerToken(): String?{
        if(token == null) return null
        return token!!.accessToken
    }

    fun clearAll() {
        token = null
        user = null
    }

}