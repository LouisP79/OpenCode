package com.quickstore.ui.useCase.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.quickstore.data.Pageable
import com.quickstore.data.address.model.AddressModel
import com.quickstore.data.address.request.AddressRequest
import com.quickstore.data.cart.model.CartModel
import com.quickstore.data.cart.request.AddCartRequest
import com.quickstore.data.category.model.CategoryModel
import com.quickstore.data.district.model.DistrictModel
import com.quickstore.data.product.model.ProductModel
import com.quickstore.data.user.request.ChangePwdRequest
import com.quickstore.data.user.request.UpdateUserInfoRequest
import com.quickstore.ui.useCase.main.repository.MainRepository
import com.quickstore.util.repository.RepoResponse

class MainViewModel constructor(private val mainRepository: MainRepository): ViewModel(){

    fun getProductList(page: Int , searchQuery: String = "", categoryId: Long = 0L): LiveData<RepoResponse<Pageable<ProductModel>>>{
        return mainRepository.getProductList(page, searchQuery, categoryId)
    }

    fun getCategoryList(page: Int): LiveData<RepoResponse<Pageable<CategoryModel>>>{
        return mainRepository.getCategoryList(page)
    }

    fun addCart(token: String, addCartRequest: AddCartRequest): LiveData<RepoResponse<Void>>{
        return mainRepository.addCart(token, addCartRequest)
    }

    fun decreaseCart(token: String, productId: Long): LiveData<RepoResponse<Void>>{
        return mainRepository.decreaseCart(token, productId)
    }

    fun listCart(token: String): LiveData<RepoResponse<CartModel>>{
        return mainRepository.listCart(token)
    }

    fun deleteProductCart(token: String, productId: Long): LiveData<RepoResponse<Void>>{
        return mainRepository.deleteProductCart(token, productId)
    }

    fun changePwd(token: String, changePwdRequest: ChangePwdRequest): LiveData<RepoResponse<Void>>{
        return mainRepository.changePwd(token, changePwdRequest)
    }

    fun updateUserInfo(idUser: Long, token: String, updateUserInfoRequest: UpdateUserInfoRequest): LiveData<RepoResponse<Void>>{
        return mainRepository.updateUserInfo(idUser, token, updateUserInfoRequest)
    }

    fun getDistrictList(): LiveData<RepoResponse<List<DistrictModel>>>{
        return mainRepository.getDistrictList()
    }

    fun getAddressList(token: String): LiveData<RepoResponse<List<AddressModel>>>{
        return mainRepository.getAddressList(token)
    }

    fun createAddress(token: String, addressRequest: AddressRequest): LiveData<RepoResponse<AddressModel>>{
        return mainRepository.createAddress(token, addressRequest)
    }

    fun deleteAddress(token: String, idAddress: Long): LiveData<RepoResponse<Void>>{
        return mainRepository.deleteAddress(token, idAddress)
    }
}