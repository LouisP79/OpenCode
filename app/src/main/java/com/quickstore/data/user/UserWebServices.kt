package com.quickstore.data.user

import com.quickstore.data.RestConstant
import com.quickstore.data.user.model.UserModel
import com.quickstore.data.user.request.ChangePwdRequest
import com.quickstore.data.user.request.RecoverPwdRequest
import com.quickstore.data.user.request.RegisterRequest
import com.quickstore.data.user.request.UpdateUserInfoRequest
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * @author SudTechnologies
 */
interface UserWebServices {

    @POST(RestConstant.ENDPOINT_REGISTER_USER)
    fun register(@Header("Authorization") authCredentials: String,
                 @Body request: RegisterRequest): Observable<UserModel>

    @GET(RestConstant.ENDPOINT_USER_INFO)
    fun userInfo(@Header("Authorization") token:  String): Observable<UserModel>

    @POST(RestConstant.ENDPOINT_USER_RECOVER_PASS)
    fun recoverPwd(@Header("Authorization") authCredentials: String,
                   @Body request: RecoverPwdRequest): Call<Void>

    @POST(RestConstant.ENDPOINT_USER_CHANGE_PASS)
    fun changePwd(@Header("Authorization") token: String,
                  @Body request: ChangePwdRequest): Call<Void>

    @POST(RestConstant.ENDPOINT_USER + "/{id}")
    fun updateUserInfo(@Path("id") id: Long,
                  @Header("Authorization") token: String,
                  @Body userInfoRequest: UpdateUserInfoRequest): Call<Void>
}
