package com.quickstore.data.token

import com.quickstore.data.RestConstant
import com.quickstore.data.token.model.TokenModel
import io.reactivex.Observable
import retrofit2.Call

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenWebServices {

    @FormUrlEncoded
    @POST(RestConstant.ENDPOINT_TOKEN)
    fun refreshToken(@Header("Authorization") authCredentials: String,
                     @Field("refresh_token") refreshToken: String,
                     @Field("grant_type") grantType: String): Call<TokenModel>

    @FormUrlEncoded
    @POST(RestConstant.ENDPOINT_TOKEN)
    fun requestAccessToken(@Header("Authorization") authCredentials: String,
                           @Field("username") userName: String,
                           @Field("password") password: String,
                           @Field("grant_type") grantType: String): Observable<TokenModel>
}
