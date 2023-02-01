package com.quickstore.firebaseCloudMessaging

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.quickstore.data.firebaseToken.FirebaseTokenWebServices
import com.quickstore.data.firebaseToken.request.FirebaseTokenRequest
import com.quickstore.preferences.ApplicationPreferences
import com.quickstore.util.core.showNotification
import org.koin.android.ext.android.inject


private val TAG = MyFirebaseMessagingService::class.java.simpleName
private const val TOPIC = "quick_store"
const val ACTION_KICK_USER = "com.quickstore.firebaseCloudMessaging.action.KICK_USER"
const val ACTION_UPDATE_RECORD = "com.quickstore.firebaseCloudMessaging.action.UPDATE_RECORD"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val applicationPreferences: ApplicationPreferences by inject()
    private val firebaseTokenWebServices: FirebaseTokenWebServices by inject()

    override fun onNewToken(refreshedToken: String) {
        super.onNewToken(refreshedToken)

        if(!applicationPreferences.firebaseToken.isNullOrBlank() && applicationPreferences.token != null)
            restDeleteFirebaseToken(applicationPreferences.firebaseToken!!)

        applicationPreferences.firebaseToken = refreshedToken
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        if(applicationPreferences.token != null)
            restCreateFirebaseToken(refreshedToken)

        Log.e(TAG, "Refreshed token: $refreshedToken")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.data.containsKey("logout")){
            kickUser()
        }else if(remoteMessage.data.containsKey("title") && remoteMessage.data.containsKey("message")){
            updateRecordList()
            showNotification(
                this,
                remoteMessage.data["title"]!!,
                remoteMessage.data["message"]!!
            )
        }
    }

    private fun kickUser() {
        val broadcast = Intent()
        broadcast.action = ACTION_KICK_USER
        sendBroadcast(broadcast)
    }

    private fun updateRecordList() {
        val broadcast = Intent()
        broadcast.action = ACTION_UPDATE_RECORD
        sendBroadcast(broadcast)
    }

    private fun restDeleteFirebaseToken(firebaseToken: String) = firebaseTokenWebServices.delete(applicationPreferences.token!!.accessToken, FirebaseTokenRequest(firebaseToken)).execute()
    private fun restCreateFirebaseToken(firebaseToken: String) = firebaseTokenWebServices.create(applicationPreferences.token!!.accessToken, FirebaseTokenRequest(firebaseToken)).execute()
}
