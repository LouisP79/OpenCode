package com.quickstore.ui.useCase.main.fragment.shopping

import android.os.Bundle
import android.view.View
import com.quickstore.R
import com.quickstore.ui.base.fragment.BaseCardFragment
import kotlinx.android.synthetic.main.content_order_created.*

const val DELIVERY_TIME =  "delivery_time"
const val DELIVERY_DATE = "delivery_date"
const val ORDER_ID = "order_id"
class OrderCreatedFragment : BaseCardFragment() {

    companion object{
        fun newInstance(deliveryDate: String, deliveryTime: String, orderId: Long): OrderCreatedFragment{
            val fragment = OrderCreatedFragment()
            val bundle = Bundle()
            bundle.putString(DELIVERY_DATE, deliveryDate)
            bundle.putString(DELIVERY_TIME, deliveryTime)
            bundle.putLong(ORDER_ID, orderId)
            fragment.arguments = bundle
            return fragment;
        }
    }

    private var listener: (()->Unit)? = null

    override val layoutResourceId: Int
        get() = R.layout.fragment_order_created


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        orderNumber.text = getString(R.string.order_number, requireArguments().getLong(ORDER_ID))
        deliveryDate.text = requireArguments().getString(DELIVERY_DATE)
        deliveryTime.text = requireArguments().getString(DELIVERY_TIME)

        continueBuying.setOnClickListener { listener?.invoke() }
    }

    fun setOnBackClickListener(listener: ()->Unit){
        this.listener = listener
    }

}