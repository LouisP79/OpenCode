package com.quickstore.data.timeDelivery

import com.quickstore.data.RestConstant
import com.quickstore.data.timeDelivery.model.TimeDeliveryModel
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Header

interface TimeDeliveryWebServices {

    @GET(RestConstant.ENDPOINT_TIME_DELIVERY_LIST)
    fun timeDeliveryList(@Header("Authorization") token: String): Call<List<TimeDeliveryModel>>

}
