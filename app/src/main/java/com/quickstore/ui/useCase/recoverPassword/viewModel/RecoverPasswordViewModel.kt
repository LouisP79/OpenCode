package com.quickstore.ui.useCase.recoverPassword.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.quickstore.data.user.request.RecoverPwdRequest
import com.quickstore.ui.useCase.recoverPassword.repository.RecoverPasswordRepository
import com.quickstore.util.repository.RepoResponse

class RecoverPasswordViewModel constructor(private val recoverPasswordRepository: RecoverPasswordRepository): ViewModel(){

    fun recoverPassword(recoverPwdRequest: RecoverPwdRequest): LiveData<RepoResponse<Void>>{
        return recoverPasswordRepository.recoverPassword(recoverPwdRequest)
    }
}