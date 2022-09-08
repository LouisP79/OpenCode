package com.quickstore.data.firebaseToken

import com.quickstore.data.RestConstant
import com.quickstore.data.firebaseToken.request.FirebaseTokenRequest
import retrofit2.Call
import retrofit2.http.*

interface FirebaseTokenWebServices {

    @POST(RestConstant.ENDPOINT_FIREBASE_TOKEN)
    fun create(@Header("Authorization") token: String,
               @Body request: FirebaseTokenRequest): Call<Void>

    @DELETE(RestConstant.ENDPOINT_FIREBASE_TOKEN)
    fun delete(@Header("Authorization") token: String,
               @Body request: FirebaseTokenRequest): Call<Void>

}
