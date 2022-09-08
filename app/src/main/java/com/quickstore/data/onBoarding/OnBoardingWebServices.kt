package com.quickstore.data.onBoarding

import com.quickstore.data.RestConstant
import com.quickstore.data.onBoarding.model.OnBoardingModel
import retrofit2.Call

import retrofit2.http.GET

interface OnBoardingWebServices {

    @GET(RestConstant.ENDPOINT_ON_BOARDING)
    fun onBoarding(): Call<List<OnBoardingModel>>

}
