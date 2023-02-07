package com.quickstore.data.product

import com.quickstore.data.Pageable
import com.quickstore.data.RestConstant
import com.quickstore.data.product.model.ProductModel
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val PAGE = "page"
const val SIZE = "size"
const val SEARCH_QUERY = "search_query"
const val CATEGORY = "category"
interface ProductWebServices {

    @GET(RestConstant.ENDPOINT_PRODUCT_LIST)
    fun productList(@Query(PAGE) page: Int,
                    @Query(SIZE) size: Int = 20): Call<Pageable<ProductModel>>

    @GET(RestConstant.ENDPOINT_PRODUCT_LIST)
    fun productList(@Query(PAGE) page: Int,
                    @Query(SEARCH_QUERY,) searchQuery: String,
                    @Query(SIZE) size: Int = 20): Call<Pageable<ProductModel>>

    @GET(RestConstant.ENDPOINT_PRODUCT_LIST)
    fun productList(@Query(PAGE) page: Int,
                    @Query(CATEGORY) category: Long,
                    @Query(SIZE) size: Int = 20): Call<Pageable<ProductModel>>

    @GET(RestConstant.ENDPOINT_PRODUCT_LIST)
    fun productList(@Query(PAGE) page: Int,
                    @Query(SEARCH_QUERY) searchQuery: String,
                    @Query(CATEGORY) category: Long,
                    @Query(SIZE) size: Int = 20): Call<Pageable<ProductModel>>

}
