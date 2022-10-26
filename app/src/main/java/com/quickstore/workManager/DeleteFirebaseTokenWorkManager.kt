package com.quickstore.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.quickstore.data.firebaseToken.FirebaseTokenWebServices
import com.quickstore.data.firebaseToken.request.FirebaseTokenRequest
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val ACCESS_TOKEN = "access_token"
const val FIREBASE_TOKEN = "firebase_token"
const val DFTAG = "deleteFirebaseTokenWMTAG"
@OptIn(KoinApiExtension::class)
class DeleteFirebaseTokenWorkManager(val context: Context, workerParams: WorkerParameters): Worker(context, workerParams), KoinComponent {

    private val firebaseTokenWebServices: FirebaseTokenWebServices by inject()

    override fun doWork(): Result {
        val accessToken = inputData.getString(ACCESS_TOKEN)
        val firebaseToken = inputData.getString(FIREBASE_TOKEN)
        if(accessToken != null && firebaseToken != null)
            firebaseTokenWebServices.delete(accessToken, FirebaseTokenRequest(firebaseToken)).execute()

        return Result.success()
    }
}