package com.quickstore.ui.useCase.main.fragment.shopping

import android.os.Bundle
import android.view.View
import com.quickstore.R
import com.quickstore.data.address.model.AddressModel
import com.quickstore.ui.base.fragment.BaseCardFragment
import com.quickstore.ui.useCase.main.adapter.AddressAdapter
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.content_shopping_addresses_list.*
import kotlinx.android.synthetic.main.fragment_shopping_addresses_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class ShoppingAddressesListFragment : BaseCardFragment() {

    companion object{
        fun newInstance() = ShoppingAddressesListFragment()
    }

    private var selectedListener: ((address: AddressModel)->Unit)? = null

    override val layoutResourceId: Int
        get() = R.layout.fragment_shopping_addresses_list

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: AddressAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener { back() }

        setup()
    }

    private fun setup() {
        adapter = AddressAdapter()
        addresses.adapter = adapter

        adapter.setOnSelectedClickListener {
            selectedListener?.invoke(it)
            back()
        }

        restAddresses()
    }

    private fun restAddresses() {
        loadingAddresses.visibility = View.VISIBLE
        viewModel.getAddressList(applicationPreferences.getBearerToken()!!
        ).observe(viewLifecycleOwner,
            { response ->
                when(response){
                    null -> unknownError(null)
                    else ->{
                        if(response.dataResponse != null){
                            if(response.dataResponse.isSuccessful){
                                adapter.setItemsVisibility(response.dataResponse.body() as MutableList<AddressModel>, radioVisibility = true, deleteVisibility = false)
                            }else errorCode(response.dataResponse.code())
                        }else errorConnection(response.throwable!!)
                    }
                }
                loadingAddresses.visibility = View.GONE
            })
    }

    fun setOnSelectedClickListener(listener: (address: AddressModel)->Unit){
        this.selectedListener = listener
    }

}