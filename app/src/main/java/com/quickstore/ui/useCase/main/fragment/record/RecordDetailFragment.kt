package com.quickstore.ui.useCase.main.fragment.record

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.quickstore.R
import com.quickstore.data.order.model.OrderModel
import com.quickstore.ui.base.fragment.BaseCardFragment
import com.quickstore.ui.useCase.main.activity.MainActivity
import com.quickstore.ui.useCase.main.adapter.RecordDetailAdapter
import kotlinx.android.synthetic.main.content_record_detail.*
import kotlinx.android.synthetic.main.fragment_create_address.*

const val ORDER = "order"
class RecordDetailFragment : BaseCardFragment() {

    companion object{
        fun newInstance(orderModel: OrderModel): RecordDetailFragment{
            val fragment = RecordDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(ORDER, orderModel)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_record_detail

    private lateinit var adapter: RecordDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener { back(); (activity as MainActivity).showWhatsapp() }
        setup()
    }

    private fun setup() {
        val order = requireArguments().getParcelable<OrderModel>(ORDER)

        date.text = order!!.date
        deliveryDate.text = order.deliveryDate
        deliveryTime.text = order.deliveryTime
        addressTag.text = order.deliveryTag
        orderNumber.text = getString(R.string.order_number2,order.id)
        address.text = order.deliveryAddress
        countProducts.text = getString(R.string.product_number,order.details.size)
        var subTotalVal = 0.0
        for(it in order.details){
            subTotalVal+= it.subTotal
        }
        subTotal.text = getString(R.string.blank_coin,subTotalVal)
        delivery.text = getString(R.string.blank_coin,order.deliveryCost)
        total.text = getString(R.string.blank_coin,order.total)

        if(!order.statusComment.isNullOrEmpty()){
            comment.visibility = View.VISIBLE
            comment.text = order.statusComment
        }

        when(order.status){
            0->{
                orderStatus.text = getString(R.string.pending)
                orderStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorItemBottomNavigationView))
                orderStatus.setBackgroundResource(R.drawable.shape_order_pending)
            }
            1->{
                orderStatus.text = getString(R.string.passed)
                orderStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                orderStatus.setBackgroundResource(R.drawable.shape_order_passed)
            }
            2->{
                orderStatus.text = getString(R.string.delivered)
                orderStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                orderStatus.setBackgroundResource(R.drawable.shape_order_delivered)
            }
            3->{
                orderStatus.text = getString(R.string.canceled)
                orderStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                orderStatus.setBackgroundResource(R.drawable.shape_order_canceled)
            }
        }

        adapter = RecordDetailAdapter()
        details.adapter = adapter
        adapter.items = order.details
    }

}