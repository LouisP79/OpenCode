package com.quickstore.ui.useCase.register.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.quickstore.R
import com.quickstore.data.user.request.RegisterRequest
import com.quickstore.ui.base.activity.BaseActivity
import com.quickstore.ui.useCase.register.viewModel.RegisterViewModel
import com.quickstore.util.extencions.validateEmail
import com.quickstore.util.extencions.validateEmpty
import com.quickstore.util.extencions.validateLength
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.content_register.*
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import java.net.HttpURLConnection

class RegisterActivity : BaseActivity() {

    override val layoutResourceId: Int
        get() = R.layout.activity_register

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        addListener()
    }

    private fun addListener() {
        send.setOnClickListener { if(validate()) restCreateUser() }
        passAgain.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if(validate()) restCreateUser()
                true
            }else false
        }
    }

    private fun restCreateUser() {
        send.startAnimation()
        val request = RegisterRequest(email.text.toString(),
            pass.text.toString(),
            name.text.toString(),
            lastName.text.toString(),
            phone.text.toString())

        viewModel.register(request)
            .observe(this,
                { response ->
                    when(response){
                        null -> unknownError(null)
                        else ->{
                            if(response.flatMapResponse != null && response.subscribeResponse != null){
                                successLoginRegister(response.subscribeResponse!!, response.flatMapResponse!!)
                            }else{
                                when(val it = response.throwable!!){
                                    is HttpException ->{
                                        if(it.code() == HttpURLConnection.HTTP_CONFLICT)
                                            validateMessage(getString(R.string.srt_exist_email))
                                        else errorCode(it.code())
                                    }else -> errorConnection(it)
                                }
                            }
                            response.disposable?.addTo(compositeDisposable)
                        }
                    }
                    send.revertAnimation()
                })
    }

    private fun validate(): Boolean{
        var evaluate = true

        if(!name.validateEmpty(R.string.str_register_validate_name)) evaluate = false
        if(!name.validateLength(2, R.string.str_validate_name_last_name)) evaluate = false

        if(!lastName.validateEmpty(R.string.str_register_validate_last_name)) evaluate = false
        if(!lastName.validateLength(2, R.string.str_validate_name_last_name)) evaluate = false

        if(!phone.validateEmpty(R.string.str_register_validate_phone)) evaluate = false
        if(!phone.validateLength(9, R.string.str_validate_phone)) evaluate = false

        if(!pass.validateEmpty(R.string.str_register_validate_password)) evaluate = false
        if(!pass.validateLength(6, R.string.str_validate_pass)) evaluate = false

        if(!passAgain.validateEmpty(R.string.str_register_validate_password)) evaluate = false
        if(!passAgain.validateLength(6, R.string.str_validate_pass)) evaluate = false

        if(!email.validateEmpty(R.string.str_register_validate_email_not_empty)) evaluate = false
        if(!email.validateEmail(R.string.str_register_validate_email_valid)) evaluate = false

        if(passAgain.text.toString() != pass.text.toString()){
            passAgain.error = getString(R.string.str_register_validate_same_pass)
            evaluate = false
        }

        return evaluate
    }
}