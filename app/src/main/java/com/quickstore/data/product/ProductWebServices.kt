package com.quickstore.data.product

import com.quickstore.data.Pageable
import com.quickstore.data.RestConstant
import com.quickstore.data.product.model.ProductModel
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

const val PAGE = "page"
const val SEARCH_QUERY = "searchQuery"
const val CATEGORY = "category"
interface ProductWebServices {

    @GET(RestConstant.ENDPOINT_PRODUCT)
    fun productList(@Query(PAGE) page: Int): Call<Pageable<ProductModel>>

    @GET(RestConstant.ENDPOINT_PRODUCT)
    fun productList(@Query(PAGE) page: Int,
                    @Query(SEARCH_QUERY) searchQuery: String): Call<Pageable<ProductModel>>

    @GET(RestConstant.ENDPOINT_PRODUCT)
    fun productList(@Query(PAGE) page: Int,
                    @Query(CATEGORY) category: Long): Call<Pageable<ProductModel>>

    @GET(RestConstant.ENDPOINT_PRODUCT)
    fun productList(@Query(PAGE) page: Int,
                    @Query(SEARCH_QUERY) searchQuery: String,
                    @Query(CATEGORY) category: Long): Call<Pageable<ProductModel>>

}
