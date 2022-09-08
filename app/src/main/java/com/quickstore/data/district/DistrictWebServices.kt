package com.quickstore.data.district

import com.quickstore.data.RestConstant
import com.quickstore.data.district.model.DistrictModel
import retrofit2.Call
import retrofit2.http.*

/**
 * @author SudTechnologies
 */

interface DistrictWebServices {

    @GET(RestConstant.ENDPOINT_DISTRICT)
    fun districtsList(): Call<List<DistrictModel>>
}
