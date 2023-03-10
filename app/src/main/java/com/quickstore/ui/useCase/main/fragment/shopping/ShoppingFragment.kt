package com.quickstore.ui.useCase.main.fragment.shopping

import android.os.Bundle
import android.view.View
import com.quickstore.R
import com.quickstore.data.cart.model.CartItemsModel
import com.quickstore.data.cart.model.CartModel
import com.quickstore.data.cart.request.AddCartRequest
import com.quickstore.data.deliveryCost.model.DeliveryCostModel
import com.quickstore.ui.base.fragment.BaseFragment
import com.quickstore.ui.useCase.main.activity.MainActivity
import com.quickstore.ui.useCase.main.adapter.ShoppingCartAdapter
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.util.core.hashString
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_shopping.*
import kotlinx.android.synthetic.main.content_shopping.next
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.util.*

class ShoppingFragment : BaseFragment() {

    companion object{
        fun newInstance() = ShoppingFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_shopping

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: ShoppingCartAdapter
    private var deliveryCost = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setup()
        addListener()
    }

    private fun setup() {
        loadingProducts.visibility = View.VISIBLE

        adapter = ShoppingCartAdapter()
        shoppingCart.adapter = adapter

        restShopping()
    }

    fun resetShoppingList(){
        adapter.cleanItems()
        restShopping()
    }

    private fun addListener() {
        swipeRefresh.setOnRefreshListener {resetShoppingList()}
        adapter.cartListener = object: ShoppingCartAdapter.CartListener{
            override fun onDelete(productId: Long, position: Int) {
                loadingProducts.visibility = View.VISIBLE
                restDeleteProductCart(productId, position)
            }
            override fun onAdd(productId: Long, quantityAdded: Double, position: Int) {
                restAddProductCart(productId, quantityAdded, position)
            }
            override fun onSubtract(productId: Long, quantitySubtracted: Double, position: Int) {
                restDecreaseProductCart(productId, quantitySubtracted, position)
            }
        }
        next.setOnClickListener{
            if(adapter.itemCount > 0) {
                val shoppingAddressFragment = ShoppingAddressScheduleFragment.newInstance(adapter.items, deliveryCost)
                shoppingAddressFragment.setOnRefreshClickListener { resetShoppingList(); (activity as MainActivity).refreshRecords() }
                shoppingAddressFragment.setOnBackClickListener { (activity as MainActivity).callMain() }
                addFragmentWithEffect(shoppingAddressFragment)
            }else showToast(R.string.cart_items_zero)
        }
    }

    private fun restShopping() {
        viewModel.listCart(applicationPreferences.token!!.accessToken)
            .observe(viewLifecycleOwner
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.flatMapResponse != null && response.subscribeResponse != null) {
                            successResponse(response.flatMapResponse!!, response.subscribeResponse!!)
                        } else {
                            errorConnection(response.throwable!!)
                        }
                        response.disposable?.addTo(compositeDisposable)
                    }
                }
                loadingProducts.visibility = View.GONE
                swipeRefresh.isRefreshing = false
            }
    }

    private fun restDeleteProductCart(productId: Long, position: Int) {
        viewModel.deleteProductCart(applicationPreferences.token!!.accessToken, productId)
            .observe(viewLifecycleOwner
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                if (adapter.deleteItem(position))
                                    emptyList.visibility = View.VISIBLE
                            } else errorCode(response.dataResponse.code())
                        } else errorConnection(response.throwable!!)
                    }
                }
                loadingProducts.visibility = View.GONE
            }
    }

    private fun restAddProductCart(productId: Long, quantityAdded: Double, position: Int) {
        viewModel.addCart(applicationPreferences.token!!.accessToken, AddCartRequest(productId, quantityAdded))
            .observe(viewLifecycleOwner
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                adapter.confirmAddSubtract(quantityAdded, position, true)
                                recalculateSubTotal()
                            } else errorCode(response.dataResponse.code())
                        } else errorConnection(response.throwable!!)
                    }
                }
                loadingProducts.visibility = View.GONE
            }
    }

    private fun restDecreaseProductCart(productId: Long, quantitySubtracted: Double, position: Int) {
        viewModel.decreaseCart(applicationPreferences.token!!.accessToken, productId)
            .observe(viewLifecycleOwner
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                adapter.confirmAddSubtract(quantitySubtracted, position, false)
                                recalculateSubTotal()
                            } else errorCode(response.dataResponse.code())
                        } else errorConnection(response.throwable!!)
                    }
                }
                loadingProducts.visibility = View.GONE
            }
    }

    private fun successResponse(response: CartModel, subResponse: DeliveryCostModel) {
        if(response.items.isEmpty())
            emptyList.visibility = View.VISIBLE
        else{
            subTotal.text = getString(R.string.blank_coin, response.total)
            adapter.items = response.items as MutableList<CartItemsModel>
            emptyList.visibility = View.GONE
        }
        deliveryCost = subResponse.cost
        delivery.text = getString(R.string.blank_coin, subResponse.cost)
        total.text = getString(R.string.blank_coin, response.total + subResponse.cost)
    }

    private fun recalculateSubTotal(){
        var subTotalValue = 0.0
        for (item in adapter.items){
            subTotalValue += item.subTotalPerProduct
        }
        subTotal.text = getString(R.string.blank_coin, subTotalValue)
        total.text = getString(R.string.blank_coin, subTotalValue + deliveryCost)
    }
}