package com.quickstore.ui.useCase.login.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quickstore.data.RestConstant
import com.quickstore.data.token.TokenWebServices
import com.quickstore.data.token.model.TokenModel
import com.quickstore.data.user.UserWebServices
import com.quickstore.data.user.model.UserModel
import com.quickstore.util.repository.RepoRxResponse
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LoginRepository constructor(private val tokenWebServices: TokenWebServices, private val userWebServices: UserWebServices) {

    fun login(email: String, password: String): LiveData<RepoRxResponse<TokenModel, UserModel>>{
        val data = MutableLiveData<RepoRxResponse<TokenModel, UserModel>>()
        val repo = RepoRxResponse<TokenModel, UserModel>()

        repo.disposable = tokenWebServices.requestAccessToken(
            RestConstant.Credentials().authCredentials,
            email,
            password,
            RestConstant.PASSWORD)
            .subscribeOn(Schedulers.io())
            .flatMap { response ->
                repo.flatMapResponse = response

                userWebServices.userInfo(response.accessToken)
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