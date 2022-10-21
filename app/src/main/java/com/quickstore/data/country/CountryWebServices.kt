package com.quickstore.data.country

import com.quickstore.data.RestConstant
import com.quickstore.data.country.model.CountryModel
import retrofit2.Call

import retrofit2.http.GET

interface CountryWebServices {

    @GET(RestConstant.ENDPOINT_COUNTRY_LIST)
    fun countryList(): Call<List<CountryModel>>

}
