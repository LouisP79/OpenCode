package com.quickstore.data.weekDayDelivery

import com.quickstore.data.RestConstant
import com.quickstore.data.weekDayDelivery.model.WeekDayDeliveryModel
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Header

interface WeekDayDeliveryWebServices {

    @GET(RestConstant.ENDPOINT_WEEK_DAY_DELIVERY_LIST)
    fun weekDayDeliveryList(@Header("Authorization") token: String): Call<List<WeekDayDeliveryModel>>

}
