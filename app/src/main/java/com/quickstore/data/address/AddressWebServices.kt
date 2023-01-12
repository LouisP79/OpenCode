package com.quickstore.data.address

import com.quickstore.data.RestConstant
import com.quickstore.data.address.model.AddressModel
import com.quickstore.data.address.request.AddressRequest

import retrofit2.Call
import retrofit2.http.*

/**
 * @author SudTechnologies
 */
interface AddressWebServices {

    @GET(RestConstant.ENDPOINT_ADDRESS_LIST)
    fun addressesList(@Header("Authorization") token: String): Call<List<AddressModel>>

    @GET(RestConstant.ENDPOINT_ADDRESS_LIST_MEETING_POINTS)
    fun addressesListMeetingPoints(@Header("Authorization") token: String): Call<List<AddressModel>>

    @POST(RestConstant.ENDPOINT_CREATE_ADDRESS)
    fun createAddress(@Header("Authorization") token:  String,
                      @Body request: AddressRequest): Call<AddressModel>

    @DELETE(RestConstant.ENDPOINT_DELETE_ADDRESS + "/{id}")
    fun deleteAddress(@Header("Authorization") token:  String,
                      @Path("id") idAddress: Long): Call<Void>
}
