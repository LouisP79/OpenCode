package com.quickstore.data.order

import com.quickstore.data.RestConstant
import com.quickstore.data.order.model.OrderIdModel
import com.quickstore.data.order.model.OrderModel
import com.quickstore.data.order.request.OrderRequest
import retrofit2.Call
import retrofit2.http.*

interface OrderWebServices {

    @POST(RestConstant.ENDPOINT_CREATE_ORDER)
    fun creteOrder(@Header("Authorization") token:  String,
                @Body request: OrderRequest): Call<OrderIdModel>

    @GET(RestConstant.ENDPOINT_ORDER_BY_USER_LIST)
    fun listOrder(@Header("Authorization") token:  String): Call<OrderModel>

}
