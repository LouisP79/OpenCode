package com.quickstore.ui.useCase.main.fragment.profile

import android.os.Bundle
import android.view.View
import com.quickstore.R
import com.quickstore.data.address.model.AddressModel
import com.quickstore.ui.base.fragment.BaseFragment
import com.quickstore.ui.useCase.main.adapter.AddressAdapter
import com.quickstore.ui.useCase.main.fragment.address.CreateAddressFragment
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.util.core.showAlertDialogControl
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    companion object{
        fun newInstance() = ProfileFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_profile

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: AddressAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setupAddress()
        addListener()
    }

    private fun setupAddress() {
        adapter = AddressAdapter()
        addresses.adapter = adapter
        adapter.setOnDeleteClickListener {idAddress, posi -> restDeleteAddress(idAddress, posi)}

        restAddresses()
    }

    private fun setup() {
        completeName.text = getString(R.string.blank_ss, applicationPreferences.user!!.name, applicationPreferences.user!!.lastName)
        phone.text = applicationPreferences.user!!.phone
        email.text = applicationPreferences.user!!.email
    }

    private fun addListener() {
        toolbar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.menu_edit_profile -> {
                    val editProfileFragment = EditProfileFragment.newInstance()
                    addFragmentWithEffect(editProfileFragment)
                    editProfileFragment.setOnUserInfoUpdateListener { setup() }
                    true
                }
                else -> false
            }
        }

        logout.setOnClickListener {
            showAlertDialogControl(requireContext(),
                R.string.warning, R.string.logout_alert_msg
            ) { kickUser() }
        }

        changePassword.setOnClickListener { addFragmentWithEffect(ChangePasswordFragment.newInstance()) }
        newAddress.setOnClickListener {
            val fragment = CreateAddressFragment.newInstance()
            addFragmentWithEffect(fragment)
        }
    }

    fun updateNewAddress(newAddress: AddressModel) {
        adapter.addItem(newAddress)
    }

    private fun restAddresses() {
        loadingAddresses.visibility = View.VISIBLE
        viewModel.getAddressList(applicationPreferences.getBearerToken()!!
        ).observe(viewLifecycleOwner
        ) { response ->
            when (response) {
                null -> unknownError(null)
                else -> {
                    if (response.dataResponse != null) {
                        if (response.dataResponse.isSuccessful) {
                            adapter.setItemsVisibility(
                                response.dataResponse.body() as MutableList<AddressModel>,
                                radioVisibility = false,
                                deleteVisibility = true
                            )
                        } else errorCode(response.dataResponse.code())
                    } else errorConnection(response.throwable!!)
                }
            }
            loadingAddresses.visibility = View.GONE
        }
    }

    private fun restDeleteAddress(idAddress: Long, posi: Int) {
        showAlertDialogControl(requireContext(),
            R.string.delete_address_alert_title, R.string.delete_address_alert_msg
        ) { viewModel.deleteAddress(applicationPreferences.getBearerToken()!!,
            idAddress
        ).observe(viewLifecycleOwner
        ) { response ->
            when (response) {
                null -> unknownError(null)
                else -> {
                    if (response.dataResponse != null) {
                        if (response.dataResponse.isSuccessful) {
                            adapter.deleteItem(posi)
                        } else errorCode(response.dataResponse.code())
                    } else errorConnection(response.throwable!!)
                }
            }
        }
        }
    }

}