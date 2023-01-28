package com.quickstore.ui.useCase.main.fragment.shopping

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.quickstore.R
import com.quickstore.data.address.model.AddressModel
import com.quickstore.ui.base.fragment.BaseCardFragment
import com.quickstore.ui.useCase.main.adapter.ShoppingDayDeliveryAdapter
import com.quickstore.ui.useCase.main.adapter.ShoppingTimeDeliveryAdapter
import com.quickstore.ui.useCase.main.fragment.address.CreateAddressFragment
import com.quickstore.ui.useCase.main.fragment.profile.ProfileFragment
import com.quickstore.ui.useCase.main.model.DateDeliveryModel
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.content_shopping_address_schedule.*
import kotlinx.android.synthetic.main.fragment_shopping_address_schedule.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class ShoppingAddressScheduleFragment : BaseCardFragment() {

    companion object{
        fun newInstance() = ShoppingAddressScheduleFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_shopping_address_schedule

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: ShoppingTimeDeliveryAdapter
    private lateinit var adapterDay: ShoppingDayDeliveryAdapter
    private var idAddressSelected: Long = -1
    private var timeDeliverySelected = ""
    private var dateDeliverySelected = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener { back() }

        setup()
        addListener()
    }

    private fun setup() {
        restAddresses()
        restDeliveryTime()
        restDeliveryWeekDay()

        adapter = ShoppingTimeDeliveryAdapter()
        deliveryTime.layoutManager = GridLayoutManager(context, 2)
        deliveryTime.adapter = adapter

        adapterDay = ShoppingDayDeliveryAdapter()
        deliveryDay.layoutManager = GridLayoutManager(context, 3)
        deliveryDay.adapter = adapterDay
    }

    private fun addListener() {
        newAddress.setOnClickListener {
            val fragment = CreateAddressFragment.newInstance()
            addFragmentWithEffect(fragment)
        }
        viewAllAddress.setOnClickListener {
            val shoppingAddressesListFragment = ShoppingAddressesListFragment.newInstance()
            addFragmentWithEffect(shoppingAddressesListFragment)

            shoppingAddressesListFragment.setOnSelectedClickListener {
                addressSelected(it)
            }
        }
        adapter.setOnTimeDeliveryClickListener {
            timeDeliverySelected = it
        }
        adapterDay.setOnDateDeliveryClickListener {
            dateDeliverySelected = it
        }
        next.setOnClickListener {
            if(validate()){

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
        viewModel.getAddressListMeetingPoints(applicationPreferences.getBearerToken()!!
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

    private fun restDeliveryTime() {
        loadingDeliveryTime.visibility = View.VISIBLE
        viewModel.getDeliveryTimes(applicationPreferences.getBearerToken()!!
        ).observe(viewLifecycleOwner
        ) { response ->
            when (response) {
                null -> unknownError(null)
                else -> {
                    if (response.dataResponse != null) {
                        if (response.dataResponse.isSuccessful) {
                            adapter.items = response.dataResponse.body()!!
                        } else errorCode(response.dataResponse.code())
                    } else errorConnection(response.throwable!!)
                }
            }
            loadingDeliveryTime.visibility = View.GONE
        }
    }

    private fun restDeliveryWeekDay() {
        loadingDeliveryDay.visibility = View.VISIBLE
        viewModel.getDeliveryWeekDays(applicationPreferences.getBearerToken()!!
        ).observe(viewLifecycleOwner
        ) { response ->
            when (response) {
                null -> unknownError(null)
                else -> {
                    if (response.dataResponse != null) {
                        if (response.dataResponse.isSuccessful) {
                            val cal = Calendar.getInstance()
                            var items  = mutableListOf<DateDeliveryModel>()

                            while (items.size < 3) {
                                cal.add(Calendar.DAY_OF_YEAR, 1)
                                val valueToSearch = cal.get(Calendar.DAY_OF_WEEK)
                                val valueToSearchDay = cal.get(Calendar.DAY_OF_MONTH)
                                val valueToSearchMonth = cal.get(Calendar.MONTH) + 1
                                val valueToSearchYear = cal.get(Calendar.YEAR)

                                val schedule = response.dataResponse.body()!!
                                for (sch in schedule) {
                                    if (valueToSearch == sch.weekDayNumber) {
                                        if (sch.status == 1) {
                                            var dayName = ""
                                            when (valueToSearch) {
                                                1 -> dayName = "Domingo"
                                                2 -> dayName = "Lunes"
                                                3 -> dayName = "Martes"
                                                4 -> dayName = "Miércoles"
                                                5 -> dayName = "Jueves"
                                                6 -> dayName = "Viernes"
                                                7 -> dayName = "Sábado"
                                            }
                                            dayName = if(valueToSearchDay < 10 && valueToSearchMonth < 10)
                                                "$dayName 0$valueToSearchDay/0$valueToSearchMonth/$valueToSearchYear"
                                            else if(valueToSearchDay < 10)
                                                "$dayName 0$valueToSearchDay/$valueToSearchMonth/$valueToSearchYear"
                                            else if(valueToSearchMonth < 10)
                                                "$dayName $valueToSearchDay/0$valueToSearchMonth/$valueToSearchYear"
                                            else
                                                "$dayName $valueToSearchDay/$valueToSearchMonth/$valueToSearchYear"
                                            items.add(DateDeliveryModel(dayName))
                                        }
                                        break
                                    }
                                }
                            }
                            adapterDay.items = items
                        } else errorCode(response.dataResponse.code())
                    } else errorConnection(response.throwable!!)
                }
            }
            loadingDeliveryDay.visibility = View.GONE
        }
    }

    private fun addressSelected(address: AddressModel){
        idAddressSelected = address.id
        addressTag.text = address.tag
        addressReference.text = address.address
    }

    private fun validate(): Boolean{
        var evaluate = true

        if(idAddressSelected == -1L)
            evaluate = false

        if(timeDeliverySelected.isEmpty())
            evaluate = false

        if(dateDeliverySelected.isEmpty())
            evaluate = false

        if(!evaluate)
            showToast(R.string.create_order_error)

        return evaluate
    }

}