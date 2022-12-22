package com.quickstore.data.cart

import com.quickstore.data.RestConstant
import com.quickstore.data.cart.model.CartModel
import com.quickstore.data.cart.request.AddCartRequest
import retrofit2.Call
import retrofit2.http.*

const val PRODUCT_ID = "productId"
interface CartWebServices {

    @POST(RestConstant.ENDPOINT_ADD_CART)
    fun addCart(@Header("Authorization") token:  String,
                @Body request: AddCartRequest): Call<Void>

    @POST(RestConstant.ENDPOINT_DECREASE_CART)
    fun decreaseCart(@Header("Authorization") token:  String,
                @Query(PRODUCT_ID) productId: Long): Call<Void>

    @GET(RestConstant.ENDPOINT_LIST_CART)
    fun listCart(@Header("Authorization") token:  String): Call<CartModel>

    @DELETE(RestConstant.ENDPOINT_DELETE_PRODUCT_CART)
    fun deleteProductCart(@Header("Authorization") token:  String,
                          @Query(PRODUCT_ID) productId: Long): Call<Void>

}
