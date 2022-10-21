package com.quickstore.ui.useCase.main.fragment.shopping

import android.os.Bundle
import android.view.View
import com.quickstore.R
import com.quickstore.data.address.model.AddressModel
import com.quickstore.ui.base.fragment.BaseCardFragment
import com.quickstore.ui.useCase.main.adapter.ShoppingTimeDeliveryAdapter
import com.quickstore.ui.useCase.main.fragment.address.CreateAddressFragment
import com.quickstore.ui.useCase.main.fragment.profile.ProfileFragment
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.content_shopping_address_schedule.*
import kotlinx.android.synthetic.main.fragment_shopping_address_schedule.*
import org.koin.android.viewmodel.ext.android.viewModel

class ShoppingAddressScheduleFragment : BaseCardFragment() {

    companion object{
        fun newInstance() = ShoppingAddressScheduleFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_shopping_address_schedule

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: ShoppingTimeDeliveryAdapter
    private var idAddressSelected: Long = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener { back() }

        setup()
        addListener()
    }

    private fun setup() {
        restAddresses()

        adapter = ShoppingTimeDeliveryAdapter()
        deliveryTime.adapter = adapter
    }

    private fun addListener() {
        newAddress.setOnClickListener {
            val fragment = CreateAddressFragment.newInstance()
            addFragmentWithEffect(fragment)
            fragment.setOnUserInfoUpdateListener {
                addressSelected(it)
                updateAddressInProfile(it)
            }
        }
        viewAllAddress.setOnClickListener {
            val shoppingAddressesListFragment = ShoppingAddressesListFragment.newInstance()
            addFragmentWithEffect(shoppingAddressesListFragment)

            shoppingAddressesListFragment.setOnSelectedClickListener {
                addressSelected(it)
            }
        }
    }

    private fun updateAddressInProfile(newAddress: AddressModel) {
        for(i in parentFragmentManager.fragments.size - 1 downTo 0){
            if(parentFragmentManager.fragments[i] is ProfileFragment){
                (parentFragmentManager.fragments[i] as ProfileFragment).updateNewAddress(newAddress)
            }
        }
    }

    private fun restAddresses() {
        loadingAddress.visibility = View.VISIBLE
        selectedAddress.visibility = View.GONE
        viewModel.getAddressList(applicationPreferences.getBearerToken()!!
        ).observe(viewLifecycleOwner
        ) { response ->
            when (response) {
                null -> unknownError(null)
                else -> {
                    if (response.dataResponse != null) {
                        if (response.dataResponse.isSuccessful) {
                            addressSelected(response.dataResponse.body()!![0])
                        } else errorCode(response.dataResponse.code())
                    } else errorConnection(response.throwable!!)
                }
            }
            selectedAddress.visibility = View.VISIBLE
            loadingAddress.visibility = View.GONE
        }
    }

    private fun addressSelected(address: AddressModel){
        idAddressSelected = address.id
        addressTag.text = address.tag
        addressReference.text = address.address
    }

}