package com.quickstore.ui.useCase.main.fragment.address

import android.os.Bundle
import android.view.View
import com.quickstore.R
import com.quickstore.data.address.model.AddressModel
import com.quickstore.data.address.request.AddressRequest
import com.quickstore.ui.base.fragment.BaseCardFragment
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.util.extencions.validateEmpty
import com.quickstore.util.extencions.validateLength
import kotlinx.android.synthetic.main.content_create_address.*
import kotlinx.android.synthetic.main.fragment_create_address.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.HttpURLConnection


class CreateAddressFragment : BaseCardFragment() {

    companion object{
        fun newInstance() = CreateAddressFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_create_address

    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { back() }

        setup()
        addListener()
    }

    private fun setup() {
        addressTag.setText(getString(R.string.office))
    }

    private fun addListener() {
        send.setOnClickListener { if(validate()) restCreateAddress() }
        options.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.office -> {
                    addressTag.setText(getString(R.string.office))
                    addressTagTextInputLayout.visibility = View.GONE
                }
                R.id.home -> {
                    addressTag.setText(getString(R.string.home))
                    addressTagTextInputLayout.visibility = View.GONE
                }
                R.id.other -> {
                    addressTag.setText("")
                    addressTagTextInputLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun restCreateAddress() {
        send.startAnimation()
        viewModel.createAddress(applicationPreferences.getBearerToken()!!,
            AddressRequest(addressTag.text.toString(),
                address.text.toString()))
            .observe(viewLifecycleOwner
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                showToast(R.string.create_address_success)
                                back()
                            } else {
                                when(response.dataResponse.code()){
                                    HttpURLConnection.HTTP_CONFLICT -> {
                                        showToast(getString(R.string.srt_exist_address))
                                        send.revertAnimation()
                                    }
                                    else -> {
                                        errorCode(response.dataResponse.code())
                                        send.revertAnimation()
                                    }
                                }
                            }
                        } else errorConnection(response.throwable!!)
                    }
                }
            }
    }

    private fun validate(): Boolean{
        var evaluate = true

        if(other.isChecked) {
            if (!addressTag.validateEmpty(R.string.str_register_validate_tag)) evaluate = false
            if (!addressTag.validateLength(2, R.string.str_validate_name_last_name)) evaluate = false
        }

        if(!address.validateEmpty(R.string.str_register_validate_address)) evaluate = false
        if(!address.validateLength(2, R.string.str_validate_name_last_name)) evaluate = false

        return evaluate
    }
}