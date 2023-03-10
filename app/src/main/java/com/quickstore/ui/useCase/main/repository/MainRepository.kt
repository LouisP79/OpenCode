package com.quickstore.ui.useCase.main.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quickstore.data.Pageable
import com.quickstore.data.RestConstant
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
import com.quickstore.data.deliveryCost.DeliveryCostWebServices
import com.quickstore.data.deliveryCost.model.DeliveryCostModel
import com.quickstore.data.order.OrderWebServices
import com.quickstore.data.order.model.OrderIdModel
import com.quickstore.data.order.model.OrderModel
import com.quickstore.data.order.request.OrderRequest
import com.quickstore.data.product.ProductWebServices
import com.quickstore.data.product.model.ProductModel
import com.quickstore.data.timeDelivery.TimeDeliveryWebServices
import com.quickstore.data.timeDelivery.model.TimeDeliveryModel
import com.quickstore.data.token.model.TokenModel
import com.quickstore.data.user.UserWebServices
import com.quickstore.data.user.model.UserModel
import com.quickstore.data.user.request.ChangePwdRequest
import com.quickstore.data.user.request.UpdateUserInfoRequest
import com.quickstore.data.weekDayDelivery.WeekDayDeliveryWebServices
import com.quickstore.data.weekDayDelivery.model.WeekDayDeliveryModel
import com.quickstore.util.repository.RepoResponse
import com.quickstore.util.repository.RepoRxResponse
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository constructor(private val productWebServices: ProductWebServices, private val cartWebServices: CartWebServices,
                                 private val categoryWebServices: CategoryWebServices, private val userWebServices: UserWebServices,
                                 private val addressWebServices: AddressWebServices, private val countryWebServices: CountryWebServices,
                                 private val timeDeliveryWebServices: TimeDeliveryWebServices, private val weekDayDeliveryWebServices: WeekDayDeliveryWebServices,
                                 private val orderWebServices: OrderWebServices, private val deliveryCostWebServices: DeliveryCostWebServices) {

    fun getProductList(page: Int, searchQuery: String = "", categoryId: Long = 0L): LiveData<RepoResponse<Pageable<ProductModel>>>{
        val data = MutableLiveData<RepoResponse<Pageable<ProductModel>>>()

        if(searchQuery.isNotEmpty() && categoryId != 0L){
            productWebServices.productList(page, searchQuery, categoryId)
                .enqueue(object: Callback<Pageable<ProductModel>>{
                    override fun onResponse(call: Call<Pageable<ProductModel>>, response: Response<Pageable<ProductModel>>) {
                        data.value = RepoResponse.respond(response, null)
                    }

                    override fun onFailure(call: Call<Pageable<ProductModel>>, t: Throwable) {
                        data.value = RepoResponse.respond(null, t)
                    }
                })
        }else if(searchQuery.isNotEmpty()){
            productWebServices.productList(page, searchQuery)
                .enqueue(object: Callback<Pageable<ProductModel>>{
                    override fun onResponse(call: Call<Pageable<ProductModel>>, response: Response<Pageable<ProductModel>>) {
                        data.value = RepoResponse.respond(response, null)
                    }

                    override fun onFailure(call: Call<Pageable<ProductModel>>, t: Throwable) {
                        data.value = RepoResponse.respond(null, t)
                    }
                })
        }else if (categoryId != 0L){
            productWebServices.productList(page, categoryId)
                .enqueue(object: Callback<Pageable<ProductModel>>{
                    override fun onResponse(call: Call<Pageable<ProductModel>>, response: Response<Pageable<ProductModel>>) {
                        data.value = RepoResponse.respond(response, null)
                    }

                    override fun onFailure(call: Call<Pageable<ProductModel>>, t: Throwable) {
                        data.value = RepoResponse.respond(null, t)
                    }
                })
        }else{
            productWebServices.productList(page)
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

    fun getCategoryList(page: Int): LiveData<RepoResponse<Pageable<CategoryModel>>>{
        val data = MutableLiveData<RepoResponse<Pageable<CategoryModel>>>()

        categoryWebServices.categoryList(page)
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

    fun listCart(token: String): LiveData<RepoRxResponse<CartModel, DeliveryCostModel>>{
        val data = MutableLiveData<RepoRxResponse<CartModel, DeliveryCostModel>>()
        val repo = RepoRxResponse<CartModel, DeliveryCostModel>()

        repo.disposable = cartWebServices.listCart(token)
            .subscribeOn(Schedulers.io())
            .flatMap { response ->
                repo.flatMapResponse = response

                deliveryCostWebServices.deliveryCost(token)
                    .subscribeOn(Schedulers.io())
            }
            .observeOn(Schedulers.computation())
            .subscribeBy(
                onNext = { response ->
                    repo.subscribeResponse = response
                    data.postValue(repo)
                },
                onError = {
                    repo.throwable = it
                    data.postValue(repo)
                }
            )

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

    fun getAddressListMeetingPoints(token: String): LiveData<RepoResponse<List<AddressModel>>>{
        val data = MutableLiveData<RepoResponse<List<AddressModel>>>()

        addressWebServices.addressesListMeetingPoints(token)
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

    fun getDeliveryTimes(token: String): LiveData<RepoResponse<List<TimeDeliveryModel>>>{
        val data = MutableLiveData<RepoResponse<List<TimeDeliveryModel>>>()

        timeDeliveryWebServices.timeDeliveryList(token)
            .enqueue(object: Callback<List<TimeDeliveryModel>>{
                override fun onResponse(call: Call<List<TimeDeliveryModel>>, response: Response<List<TimeDeliveryModel>>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<List<TimeDeliveryModel>>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun getDeliveryWeekDays(token: String): LiveData<RepoResponse<List<WeekDayDeliveryModel>>>{
        val data = MutableLiveData<RepoResponse<List<WeekDayDeliveryModel>>>()

        weekDayDeliveryWebServices.weekDayDeliveryList(token)
            .enqueue(object: Callback<List<WeekDayDeliveryModel>>{
                override fun onResponse(call: Call<List<WeekDayDeliveryModel>>, response: Response<List<WeekDayDeliveryModel>>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<List<WeekDayDeliveryModel>>, t: Throwable) {
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

    fun createOrder(token: String, orderRequest: OrderRequest): LiveData<RepoResponse<OrderIdModel>>{
        val data = MutableLiveData<RepoResponse<OrderIdModel>>()

        orderWebServices.creteOrder(token, orderRequest)
            .enqueue(object: Callback<OrderIdModel>{
                override fun onResponse(call: Call<OrderIdModel>, response: Response<OrderIdModel>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<OrderIdModel>, t: Throwable) {
                    data.value = RepoResponse.respond(null, t)
                }
            })

        return data
    }

    fun listOrder(token: String): LiveData<RepoResponse<List<OrderModel>>>{
        val data = MutableLiveData<RepoResponse<List<OrderModel>>>()

        orderWebServices.listOrder(token)
            .enqueue(object: Callback<List<OrderModel>>{
                override fun onResponse(call: Call<List<OrderModel>>, response: Response<List<OrderModel>>) {
                    data.value = RepoResponse.respond(response, null)
                }

                override fun onFailure(call: Call<List<OrderModel>>, t: Throwable) {
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