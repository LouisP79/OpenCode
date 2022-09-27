package com.quickstore.ui.useCase.recoverPassword.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quickstore.data.RestConstant
import com.quickstore.data.user.UserWebServices
import com.quickstore.data.user.request.RecoverPwdRequest
import com.quickstore.util.repository.RepoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecoverPasswordRepository constructor(private val userWebServices: UserWebServices) {

    fun recoverPassword(recoverPwdRequest: RecoverPwdRequest): LiveData<RepoResponse<Void>>{
        val data = MutableLiveData<RepoResponse<Void>>()

        userWebServices.recoverPwd(RestConstant.Credentials().authCredentials,
            recoverPwdRequest)
            .enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }
}