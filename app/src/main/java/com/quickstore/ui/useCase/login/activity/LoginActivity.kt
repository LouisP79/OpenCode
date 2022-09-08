package com.quickstore.ui.useCase.login.activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.quickstore.R
import com.quickstore.ui.base.activity.BaseActivity
import com.quickstore.ui.useCase.login.viewModel.LoginViewModel
import com.quickstore.ui.useCase.recoverPassword.activity.RecoverPasswordActivity
import com.quickstore.ui.useCase.register.activity.RegisterActivity
import com.quickstore.util.extencions.validateEmail
import com.quickstore.util.extencions.validateEmpty
import com.quickstore.util.extencions.validateLength
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import java.net.HttpURLConnection


class LoginActivity : BaseActivity() {

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListener()
    }

    private fun addListener() {
        next.setOnClickListener { if(validate()) restLogin() }
        register.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
        forgotPassword.setOnClickListener { startActivity(
            Intent(
                this,
                RecoverPasswordActivity::class.java
            )
        ) }
        pass.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if(validate()) restLogin()
                true
            }else false
        }
    }

    private fun restLogin() {
        next.startAnimation()
        viewModel.login(email.text.toString(), pass.text.toString())
            .observe(this,
                { response ->
                    when (response) {
                        null -> unknownError(null)
                        else -> {
                            if (response.flatMapResponse != null && response.subscribeResponse != null) {
                                successLoginRegister(
                                    response.flatMapResponse!!,
                                    response.subscribeResponse!!
                                )
                            } else {
                                when (val it = response.throwable!!) {
                                    is HttpException -> {
                                        when {
                                            it.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> validateMessage(
                                                getString(
                                                    R.string.str_login_failed
                                                )
                                            )
                                            it.code() == HttpURLConnection.HTTP_BAD_REQUEST -> validateMessage(
                                                getString(
                                                    R.string.str_login_failed_pass
                                                )
                                            )
                                            else -> errorCode(it.code())
                                        }
                                    }
                                    else -> errorConnection(it)
                                }
                            }
                            response.disposable?.addTo(compositeDisposable)
                        }
                    }
                    next.revertAnimation()
                })
    }

    private fun validate(): Boolean{
        var evaluate = true

        if(!pass.validateEmpty(R.string.str_register_validate_password)) evaluate = false
        if(!pass.validateLength(6, R.string.str_validate_pass)) evaluate = false

        if(!email.validateEmpty(R.string.str_register_validate_email_not_empty)) evaluate = false
        if(!email.validateEmail(R.string.str_register_validate_email_valid)) evaluate = false

        return evaluate
    }

}