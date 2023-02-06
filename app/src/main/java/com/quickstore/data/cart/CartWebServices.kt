package com.quickstore.data.cart

import com.quickstore.data.RestConstant
import com.quickstore.data.cart.model.CartModel
import com.quickstore.data.cart.request.AddCartRequest
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface CartWebServices {

    @POST(RestConstant.ENDPOINT_ADD_CART)
    fun addCart(@Header("Authorization") token:  String,
                @Body request: AddCartRequest): Call<Void>

    @POST(RestConstant.ENDPOINT_DECREASE_CART + "/{id}")
    fun decreaseCart(@Header("Authorization") token:  String,
                     @Path("id") idProduct: Long): Call<Void>

    @GET(RestConstant.ENDPOINT_LIST_CART)
    fun listCart(@Header("Authorization") token:  String): Observable<CartModel>

    @DELETE(RestConstant.ENDPOINT_DELETE_PRODUCT_CART  + "/{id}")
    fun deleteProductCart(@Header("Authorization") token:  String,
                          @Path("id") idProduct: Long): Call<Void>

}
