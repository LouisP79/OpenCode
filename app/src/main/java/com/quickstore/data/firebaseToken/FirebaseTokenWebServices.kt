package com.quickstore.data.firebaseToken

import com.quickstore.data.RestConstant
import com.quickstore.data.firebaseToken.request.FirebaseTokenRequest
import retrofit2.Call
import retrofit2.http.*

interface FirebaseTokenWebServices {

    @POST(RestConstant.ENDPOINT_CREATE_FIREBASE_TOKEN)
    fun create(@Header("Authorization") token: String,
               @Body request: FirebaseTokenRequest): Call<Void>

    @HTTP(method = "DELETE", path = RestConstant.ENDPOINT_DELETE_FIREBASE_TOKEN, hasBody = true)
    fun delete(@Header("Authorization") token: String,
               @Body request: FirebaseTokenRequest): Call<Void>

}
