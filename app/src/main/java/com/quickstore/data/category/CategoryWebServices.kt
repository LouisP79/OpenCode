package com.quickstore.data.category

import com.quickstore.data.Pageable
import com.quickstore.data.RestConstant
import com.quickstore.data.category.model.CategoryModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val PAGE = "page"
interface CategoryWebServices {

    @GET(RestConstant.ENDPOINT_CTEGORY)
    fun categoryList(@Query(PAGE) page: Int): Call<Pageable<CategoryModel>>

}
