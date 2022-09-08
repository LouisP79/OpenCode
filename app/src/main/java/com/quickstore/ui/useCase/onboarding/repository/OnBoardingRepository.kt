package com.quickstore.ui.useCase.onboarding.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quickstore.data.onBoarding.OnBoardingWebServices
import com.quickstore.data.onBoarding.model.OnBoardingModel
import com.quickstore.util.repository.RepoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnBoardingRepository constructor(private val onBoardingWebServices: OnBoardingWebServices) {

    fun getSlider(): LiveData<RepoResponse<List<OnBoardingModel>>>{
        val data = MutableLiveData<RepoResponse<List<OnBoardingModel>>>()

        onBoardingWebServices.onBoarding()
            .enqueue(object: Callback<List<OnBoardingModel>>{
                override fun onResponse(call: Call<List<OnBoardingModel>>, response: Response<List<OnBoardingModel>>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<List<OnBoardingModel>>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }
}