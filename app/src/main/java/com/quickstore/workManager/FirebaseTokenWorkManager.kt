package com.quickstore.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.quickstore.data.firebaseToken.FirebaseTokenWebServices
import com.quickstore.data.firebaseToken.request.FirebaseTokenRequest
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val ACTION = "action_firebase_token_wm"
const val ACTION_CREATE = "action_create"
const val ACTION_DELETE = "action_delete"
const val ACCESS_TOKEN = "access_token"
const val FIREBASE_TOKEN = "firebase_token"
const val FTAG = "FirebaseTokenWMTAG"
@OptIn(KoinApiExtension::class)
class FirebaseTokenWorkManager(val context: Context, workerParams: WorkerParameters): Worker(context, workerParams), KoinComponent {

    private val firebaseTokenWebServices: FirebaseTokenWebServices by inject()

    override fun doWork(): Result {
        when(inputData.getString(ACTION)){
            ACTION_CREATE -> createFirebaseToken()
            ACTION_DELETE -> deleteFirebaseToken()
        }

        return Result.success()
    }

    private fun deleteFirebaseToken() {
        val accessToken = inputData.getString(ACCESS_TOKEN)
        val firebaseToken = inputData.getString(FIREBASE_TOKEN)
        if(accessToken != null && firebaseToken != null)
            firebaseTokenWebServices.delete(accessToken, FirebaseTokenRequest(firebaseToken)).execute()
    }

    private fun createFirebaseToken() {
        val accessToken = inputData.getString(ACCESS_TOKEN)
        val firebaseToken = inputData.getString(FIREBASE_TOKEN)
        if(accessToken != null && firebaseToken != null)
            firebaseTokenWebServices.create(accessToken, FirebaseTokenRequest(firebaseToken)).execute()
    }
}