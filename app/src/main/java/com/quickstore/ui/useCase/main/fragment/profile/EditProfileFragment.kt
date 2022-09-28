package com.quickstore.ui.useCase.main.fragment.profile

import android.os.Bundle
import android.view.View
import com.quickstore.R
import com.quickstore.data.user.request.UpdateUserInfoRequest
import com.quickstore.ui.base.fragment.BaseCardFragment
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.util.extencions.validateEmpty
import com.quickstore.util.extencions.validateLength
import kotlinx.android.synthetic.main.content_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class EditProfileFragment : BaseCardFragment() {

    companion object{
        fun newInstance() = EditProfileFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_profile

    private var listener: (()->Unit)? = null

    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { back() }

        setup()
        addListener()
    }

    private fun setup() {
        name.setText(applicationPreferences.user!!.name)
        lastName.setText(applicationPreferences.user!!.lastName)
        phone.setText(applicationPreferences.user!!.phone)
    }

    private fun addListener() {
        send.setOnClickListener { if(validate()) restUpdateUserInfo() }
    }

    private fun restUpdateUserInfo() {
        send.startAnimation()
        viewModel.updateUserInfo(applicationPreferences.user!!.id,
            applicationPreferences.getBearerToken()!!,
            UpdateUserInfoRequest(lastName.text.toString(), name.text.toString(), phone.text.toString()))
            .observe(viewLifecycleOwner
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                val userModel = applicationPreferences.user!!
                                userModel.name = name.text.toString()
                                userModel.lastName = lastName.text.toString()
                                userModel.phone = phone.text.toString()
                                applicationPreferences.user = userModel
                                showToast(R.string.update_user_info_success)
                                listener?.invoke()
                                back()
                            } else errorCode(response.dataResponse.code())
                        } else errorConnection(response.throwable!!)
                    }
                }
                send.revertAnimation()
            }
    }

    private fun validate(): Boolean{
        var evaluate = true

        if(!name.validateEmpty(R.string.str_register_validate_name)) evaluate = false
        if(!name.validateLength(2, R.string.str_validate_name_last_name)) evaluate = false

        if(!lastName.validateEmpty(R.string.str_register_validate_last_name)) evaluate = false
        if(!lastName.validateLength(2, R.string.str_validate_name_last_name)) evaluate = false

        if(!phone.validateEmpty(R.string.str_register_validate_phone)) evaluate = false
        if(!phone.validateLength(9, R.string.str_validate_phone)) evaluate = false

        return evaluate
    }

    fun setOnUserInfoUpdateListener(listener: ()->Unit){
        this.listener = listener
    }
}