package com.quickstore.data.deliveryCost

import com.quickstore.data.RestConstant
import com.quickstore.data.deliveryCost.model.DeliveryCostModel
import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.Header

interface DeliveryCostWebServices {

    @GET(RestConstant.ENDPOINT_DELIVERY_COST)
    fun deliveryCost(@Header("Authorization") token: String): Observable<DeliveryCostModel>

}
