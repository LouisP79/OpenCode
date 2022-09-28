package com.quickstore.ui.useCase.main.fragment.home

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.quickstore.BuildConfig
import com.quickstore.R
import com.quickstore.data.RestConstant
import com.quickstore.data.cart.request.AddCartRequest
import com.quickstore.data.product.model.ProductModel
import com.quickstore.ui.base.fragment.BaseCardFragment
import com.quickstore.ui.useCase.main.viewModel.MainViewModel
import com.quickstore.util.core.glide
import kotlinx.android.synthetic.main.content_product_detail.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

const val PRODUCT = "product"

class ProductDetailFragment : BaseCardFragment() {

    companion object{
        fun newInstance(productModel: ProductModel): ProductDetailFragment {
            val instance = ProductDetailFragment()
            instance.arguments = bundleOf(PRODUCT to productModel)
            return instance
        }
    }

    private var listener: (()->Unit)? = null

    override val layoutResourceId: Int
        get() = R.layout.fragment_product_detail

    private val viewModel: MainViewModel by viewModel()
    private var productId: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener { back() }

        setup()
        addListener()
    }

    private fun setup() {
        val product = when {
            SDK_INT >= 33 -> arguments?.getParcelable(PRODUCT, ProductModel::class.java)!!
            else -> @Suppress("DEPRECATION") arguments?.getParcelable(PRODUCT)!!
        }

        productId = product.id

        glide(requireContext(), img,
            BuildConfig.URL_IMAGES + RestConstant.PRODUCT + product.images + RestConstant.ALT)

        price.text = requireContext().getString(R.string.blank_soles, product.price)
        name.text = product.name
        description.text = product.description

        increase.price = product.price
    }

    private fun addListener() {
        send2Cart.setOnClickListener { restAddCart() }
    }

    private fun restAddCart() {
        send2Cart.startAnimation()
        viewModel.addCart(applicationPreferences.token!!.accessToken, AddCartRequest(productId, increase.quantity))
            .observe(viewLifecycleOwner
            ) { response ->
                when (response) {
                    null -> unknownError(null)
                    else -> {
                        if (response.dataResponse != null) {
                            if (response.dataResponse.isSuccessful) {
                                listener?.invoke()
                                showToast(R.string.add_cart_success)
                                back()
                            } else errorCode(response.dataResponse.code())
                        } else errorConnection(response.throwable!!)
                    }
                }
                send2Cart.revertAnimation()
            }
    }

    fun setOnAddProductListener(listener: ()->Unit){
        this.listener = listener
    }
}