package com.quickstore.ui.useCase.recoverPassword.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.quickstore.R
import com.quickstore.data.user.request.RecoverPwdRequest
import com.quickstore.ui.base.activity.BaseActivity
import com.quickstore.ui.useCase.recoverPassword.viewModel.RecoverPasswordViewModel
import com.quickstore.util.extencions.validateEmail
import com.quickstore.util.extencions.validateEmpty
import kotlinx.android.synthetic.main.activity_recover_password.*
import kotlinx.android.synthetic.main.content_recover_password.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.HttpURLConnection

class RecoverPasswordActivity : BaseActivity() {

    override val layoutResourceId: Int
        get() = R.layout.activity_recover_password

    private val viewModel: RecoverPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        addListener()
    }

    private fun addListener() {
        send.setOnClickListener { if(validate()) restRecoverPass() }
        email.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if(validate()) restRecoverPass()
                true
            }else false
        }
    }

    private fun restRecoverPass(){
        send.startAnimation()
        viewModel.recoverPassword(RecoverPwdRequest(email.text.toString()))
            .observe(this
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                showToast(R.string.send_forgot_password_message)
                                finish()
                            } else {
                                when(response.dataResponse.code()){
                                    HttpURLConnection.HTTP_BAD_REQUEST -> showToast(R.string.srt_no_exist_email)
                                }
                                errorCode(response.dataResponse.code())
                            }
                        } else errorConnection(response.throwable!!)
                    }
                }
                send.revertAnimation()
            }
    }

    private fun validate(): Boolean{
        var evaluate = true

        if(!email.validateEmpty(R.string.str_register_validate_email_not_empty)) evaluate = false
        if(!email.validateEmail(R.string.str_register_validate_email_valid)) evaluate = false

        return evaluate
    }
}