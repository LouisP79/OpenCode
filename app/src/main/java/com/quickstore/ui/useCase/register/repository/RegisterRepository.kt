package com.quickstore.ui.useCase.register.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quickstore.data.RestConstant
import com.quickstore.data.token.TokenWebServices
import com.quickstore.data.token.model.TokenModel
import com.quickstore.data.user.UserWebServices
import com.quickstore.data.user.model.UserModel
import com.quickstore.data.user.request.RegisterRequest
import com.quickstore.util.repository.RepoRxResponse
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class RegisterRepository constructor(private val userWebServices: UserWebServices, private val tokenWebServices: TokenWebServices) {

    fun register(registerRequest: RegisterRequest): LiveData<RepoRxResponse<UserModel, TokenModel>>{
        val data = MutableLiveData<RepoRxResponse<UserModel, TokenModel>>()
        val repo = RepoRxResponse<UserModel, TokenModel>()

        repo.disposable = userWebServices.register(registerRequest)
            .subscribeOn(Schedulers.io())
            .flatMap { response ->
                repo.flatMapResponse = response

                tokenWebServices.requestAccessToken(
                    RestConstant.Credentials().authCredentials,
                    registerRequest.email,
                    registerRequest.password,
                    RestConstant.PASSWORD)
                    .subscribeOn(Schedulers.io())
            }
            .observeOn(Schedulers.computation())
            .subscribeBy(
                onNext = { response ->
                    repo.subscribeResponse = response
                    data.postValue(repo)
                },
                onError = {
                    repo.throwable = it
                    data.postValue(repo)
                }
            )

        return data
    }
}