package com.quickstore.ui.useCase.register.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.quickstore.data.token.model.TokenModel
import com.quickstore.data.user.model.UserModel
import com.quickstore.data.user.request.RegisterRequest
import com.quickstore.ui.useCase.register.repository.RegisterRepository
import com.quickstore.util.repository.RepoRxResponse

class RegisterViewModel constructor(private val registerRepository: RegisterRepository): ViewModel(){

    fun register(registerRequest: RegisterRequest): LiveData<RepoRxResponse<UserModel, TokenModel>>{
        return registerRepository.register(registerRequest)
    }
}