package com.quickstore.ui.useCase.register.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quickstore.data.RestConstant
import com.quickstore.data.country.CountryWebServices
import com.quickstore.data.country.model.CountryModel
import com.quickstore.data.onBoarding.model.OnBoardingModel
import com.quickstore.data.token.TokenWebServices
import com.quickstore.data.token.model.TokenModel
import com.quickstore.data.user.UserWebServices
import com.quickstore.data.user.model.UserModel
import com.quickstore.data.user.request.RegisterRequest
import com.quickstore.util.repository.RepoResponse
import com.quickstore.util.repository.RepoRxResponse
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository constructor(private val userWebServices: UserWebServices, private val tokenWebServices: TokenWebServices,
                                        private val countryWebServices: CountryWebServices) {

    fun register(registerRequest: RegisterRequest): LiveData<RepoRxResponse<UserModel, TokenModel>>{
        val data = MutableLiveData<RepoRxResponse<UserModel, TokenModel>>()
        val repo = RepoRxResponse<UserModel, TokenModel>()

        repo.disposable = userWebServices.register(
            RestConstant.Credentials().authCredentials,
            registerRequest)
            .subscribeOn(Schedulers.io())
            .flatMap { response ->
                repo.flatMapResponse = response

                tokenWebServices.requestAccessToken(
                    RestConstant.Credentials().authCredentials,
                    registerRequest.email,
                    registerRequest.password)
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

    fun getCountries(): LiveData<RepoResponse<List<CountryModel>>>{
        val data = MutableLiveData<RepoResponse<List<CountryModel>>>()

        countryWebServices.countryList()
            .enqueue(object: Callback<List<CountryModel>> {
                override fun onResponse(call: Call<List<CountryModel>>, response: Response<List<CountryModel>>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<List<CountryModel>>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }
}