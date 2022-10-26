package com.quickstore.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.quickstore.data.firebaseToken.FirebaseTokenWebServices
import com.quickstore.data.firebaseToken.request.FirebaseTokenRequest
import com.quickstore.preferences.ApplicationPreferences
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val CFTAG = "createFirebaseTokenWMTAG"
@OptIn(KoinApiExtension::class)
class CreateFirebaseTokenWorkManager(val context: Context, workerParams: WorkerParameters): Worker(context, workerParams), KoinComponent {

    private val firebaseTokenWebServices: FirebaseTokenWebServices by inject()
    private val applicationPreferences: ApplicationPreferences by inject()

    override fun doWork(): Result {
        if (applicationPreferences.firebaseToken!=null)
            firebaseTokenWebServices.create(applicationPreferences.token!!.accessToken, FirebaseTokenRequest(applicationPreferences.firebaseToken!!)).execute()

        return Result.success()
    }
}