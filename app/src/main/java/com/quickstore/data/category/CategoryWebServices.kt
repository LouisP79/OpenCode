package com.quickstore.data.category

import com.quickstore.data.Pageable
import com.quickstore.data.RestConstant
import com.quickstore.data.category.model.CategoryModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val PAGE = "page"
const val SIZE = "size"
interface CategoryWebServices {

    @GET(RestConstant.ENDPOINT_CATEGORY_LIST)
    fun categoryList(@Query(PAGE) page: Int,
                     @Query(SIZE) size: Int = 20): Call<Pageable<CategoryModel>>

}
