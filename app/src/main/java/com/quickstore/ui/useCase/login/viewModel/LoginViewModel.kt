package com.quickstore.ui.useCase.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.quickstore.data.token.model.TokenModel
import com.quickstore.data.user.model.UserModel
import com.quickstore.ui.useCase.login.repository.LoginRepository
import com.quickstore.util.repository.RepoRxResponse

class LoginViewModel constructor(private val loginRepository: LoginRepository): ViewModel(){

    fun login(email: String, password: String): LiveData<RepoRxResponse<TokenModel, UserModel>>{
        return loginRepository.login(email, password)
    }
}