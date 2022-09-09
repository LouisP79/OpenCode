package com.quickstore.ui.useCase.main.fragment.profile

import android.os.Bundle
import android.view.View
import com.quickstore.R
import com.quickstore.data.user.request.ChangePwdRequest
import com.quickstore.ui.base.fragment.BaseCardFragment
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.util.extencions.validateEmpty
import com.quickstore.util.extencions.validateLength
import kotlinx.android.synthetic.main.content_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import org.koin.android.viewmodel.ext.android.viewModel

class ChangePasswordFragment : BaseCardFragment() {

    companion object{
        fun newInstance() = ChangePasswordFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_change_password

    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { back() }

        addListener()
    }

    private fun addListener() {
        send.setOnClickListener { if(validate()) restChangePassword() }
    }

    private fun restChangePassword() {
        send.startAnimation()
        viewModel.changePwd(applicationPreferences.getBearerToken()!!,
            ChangePwdRequest(oldPass.text.toString(), newPass.text.toString()))
            .observe(viewLifecycleOwner,
                { response ->
                    when(response){
                        null -> unknownError(null)
                        else ->{
                            if(response.dataResponse != null){
                                if(response.dataResponse.isSuccessful){
                                    showToast(R.string.change_password_success)
                                    back()
                                }else errorCode(response.dataResponse.code())
                            }else errorConnection(response.throwable!!)
                        }
                    }
                    send.revertAnimation()
                })
    }

    private fun validate(): Boolean{
        var evaluate = true

        if(!oldPass.validateEmpty(R.string.str_register_validate_password)) evaluate = false
        if(!oldPass.validateLength(6, R.string.str_validate_pass)) evaluate = false

        if(!newPass.validateEmpty(R.string.str_register_validate_password)) evaluate = false
        if(!newPass.validateLength(6, R.string.str_validate_pass)) evaluate = false

        if(!newPassAgain.validateEmpty(R.string.str_register_validate_password)) evaluate = false
        if(!newPassAgain.validateLength(6, R.string.str_validate_pass)) evaluate = false

        if(newPassAgain.text.toString() != newPass.text.toString()){
            newPassAgain.error = getString(R.string.str_register_validate_same_pass)
            evaluate = false
        }

        if(oldPass.text.toString() == newPass.text.toString()){
            newPass.error = getString(R.string.str_register_validate_same_old_pass)
            evaluate = false
        }

        return evaluate
    }

}