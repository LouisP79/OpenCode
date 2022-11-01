package com.quickstore.ui.useCase.main.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quickstore.data.Pageable
import com.quickstore.data.address.AddressWebServices
import com.quickstore.data.address.model.AddressModel
import com.quickstore.data.address.request.AddressRequest
import com.quickstore.data.cart.CartWebServices
import com.quickstore.data.cart.model.CartModel
import com.quickstore.data.cart.request.AddCartRequest
import com.quickstore.data.category.CategoryWebServices
import com.quickstore.data.category.model.CategoryModel
import com.quickstore.data.country.CountryWebServices
import com.quickstore.data.country.model.CountryModel
import com.quickstore.data.product.ProductWebServices
import com.quickstore.data.product.model.ProductModel
import com.quickstore.data.user.UserWebServices
import com.quickstore.data.user.request.ChangePwdRequest
import com.quickstore.data.user.request.UpdateUserInfoRequest
import com.quickstore.util.repository.RepoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository constructor(private val productWebServices: ProductWebServices, private val cartWebServices: CartWebServices,
                                 private val categoryWebServices: CategoryWebServices, private val userWebServices: UserWebServices,
                                 private val addressWebServices: AddressWebServices, private val countryWebServices: CountryWebServices) {

    fun getProductList(token: String, page: Int, searchQuery: String = "", categoryId: Long = 0L): LiveData<RepoResponse<Pageable<ProductModel>>>{
        val data = MutableLiveData<RepoResponse<Pageable<ProductModel>>>()

        if(searchQuery.isNotEmpty() && categoryId != 0L){
            productWebServices.productList(token,page, searchQuery, categoryId)
                .enqueue(object: Callback<Pageable<ProductModel>>{
                    override fun onResponse(call: Call<Pageable<ProductModel>>, response: Response<Pageable<ProductModel>>) {
                        data.value = RepoResponse.respond(response, null)
                    }

                    override fun onFailure(call: Call<Pageable<ProductModel>>, t: Throwable) {
                        data.value = RepoResponse.respond(null, t)
                    }
                })
        }else if(searchQuery.isNotEmpty()){
            productWebServices.productList(token, page, searchQuery)
                .enqueue(object: Callback<Pageable<ProductModel>>{
                    override fun onResponse(call: Call<Pageable<ProductModel>>, response: Response<Pageable<ProductModel>>) {
                        data.value = RepoResponse.respond(response, null)
                    }

                    override fun onFailure(call: Call<Pageable<ProductModel>>, t: Throwable) {
                        data.value = RepoResponse.respond(null, t)
                    }
                })
        }else if (categoryId != 0L){
            productWebServices.productList(token, page, categoryId)
                .enqueue(object: Callback<Pageable<ProductModel>>{
                    override fun onResponse(call: Call<Pageable<ProductModel>>, response: Response<Pageable<ProductModel>>) {
                        data.value = RepoResponse.respond(response, null)
                    }

                    override fun onFailure(call: Call<Pageable<ProductModel>>, t: Throwable) {
                        data.value = RepoResponse.respond(null, t)
                    }
                })
        }else{
            productWebServices.productList(token, page)
                .enqueue(object: Callback<Pageable<ProductModel>>{
                    override fun onResponse(call: Call<Pageable<ProductModel>>, response: Response<Pageable<ProductModel>>) {
                        data.value = RepoResponse.respond(response, null)
                    }

                    override fun onFailure(call: Call<Pageable<ProductModel>>, t: Throwable) {
                        data.value = RepoResponse.respond(null, t)
                    }
                })
        }

        return data
    }

    fun getCategoryList(token: String, page: Int): LiveData<RepoResponse<Pageable<CategoryModel>>>{
        val data = MutableLiveData<RepoResponse<Pageable<CategoryModel>>>()

        categoryWebServices.categoryList(token, page)
            .enqueue(object: Callback<Pageable<CategoryModel>>{
                override fun onResponse(call: Call<Pageable<CategoryModel>>, response: Response<Pageable<CategoryModel>>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<Pageable<CategoryModel>>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun addCart(token: String, addCartRequest: AddCartRequest): LiveData<RepoResponse<Void>>{
        val data = MutableLiveData<RepoResponse<Void>>()

        cartWebServices.addCart(token, addCartRequest)
            .enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun decreaseCart(token: String, productId: Long): LiveData<RepoResponse<Void>>{
        val data = MutableLiveData<RepoResponse<Void>>()

        cartWebServices.decreaseCart(token, productId)
            .enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun listCart(token: String): LiveData<RepoResponse<CartModel>>{
        val data = MutableLiveData<RepoResponse<CartModel>>()

        cartWebServices.listCart(token)
            .enqueue(object: Callback<CartModel>{
                override fun onResponse(call: Call<CartModel>, response: Response<CartModel>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<CartModel>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun deleteProductCart(token: String, productId: Long): LiveData<RepoResponse<Void>>{
        val data = MutableLiveData<RepoResponse<Void>>()

        cartWebServices.deleteProductCart(token, productId)
            .enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun changePwd(token: String, changePwdRequest: ChangePwdRequest): LiveData<RepoResponse<Void>>{
        val data = MutableLiveData<RepoResponse<Void>>()

        userWebServices.changePwd(token, changePwdRequest)
            .enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun updateUserInfo(idUser: Long, token: String, updateUserInfoRequest: UpdateUserInfoRequest): LiveData<RepoResponse<Void>>{
        val data = MutableLiveData<RepoResponse<Void>>()

        userWebServices.updateUserInfo(idUser, token, updateUserInfoRequest)
            .enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun getAddressList(token: String): LiveData<RepoResponse<List<AddressModel>>>{
        val data = MutableLiveData<RepoResponse<List<AddressModel>>>()

        addressWebServices.addressesList(token)
            .enqueue(object: Callback<List<AddressModel>>{
                override fun onResponse(call: Call<List<AddressModel>>, response: Response<List<AddressModel>>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<List<AddressModel>>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun createAddress(token: String, addressRequest: AddressRequest): LiveData<RepoResponse<AddressModel>>{
        val data = MutableLiveData<RepoResponse<AddressModel>>()

        addressWebServices.createAddress(token, addressRequest)
            .enqueue(object: Callback<AddressModel>{
                override fun onResponse(call: Call<AddressModel>, response: Response<AddressModel>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<AddressModel>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun deleteAddress(token: String, idAddress: Long): LiveData<RepoResponse<Void>>{
        val data = MutableLiveData<RepoResponse<Void>>()

        addressWebServices.deleteAddress(token, idAddress)
            .enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun getCountries(): LiveData<RepoResponse<List<CountryModel>>>{
        val data = MutableLiveData<RepoResponse<List<CountryModel>>>()

        countryWebServices.countryList()
            .enqueue(object: Callback<List<CountryModel>> {
                override fun onResponse(call: Call<List<CountryModel>>, response: Response<List<CountryModel>>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<List<CountryModel>>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }


}