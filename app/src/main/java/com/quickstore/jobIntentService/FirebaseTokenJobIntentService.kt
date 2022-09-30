package com.quickstore.jobIntentService

import android.content.Intent
import androidx.core.app.JobIntentService
import com.quickstore.data.firebaseToken.FirebaseTokenWebServices
import com.quickstore.data.firebaseToken.request.FirebaseTokenRequest
import com.quickstore.preferences.ApplicationPreferences
import org.koin.android.ext.android.inject

class FirebaseTokenJobIntentService : JobIntentService() { //cambiar por workManager

    private val firebaseTokenWebServices: FirebaseTokenWebServices by inject()
    private val applicationPreferences: ApplicationPreferences by inject()

    override fun onHandleWork(intent: Intent) {
        if (applicationPreferences.firebaseToken!=null)
            restCreateFirebaseToken()
    }

    private fun restCreateFirebaseToken() = firebaseTokenWebServices.create(applicationPreferences.token!!.accessToken, FirebaseTokenRequest(applicationPreferences.firebaseToken!!)).execute()
}
