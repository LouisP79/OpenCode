package com.quickstore.ui.useCase.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quickstore.R
import com.quickstore.data.order.model.OrderDetailModel
import kotlinx.android.synthetic.main.item_record_detail.view.*

class RecordDetailAdapter: RecyclerView.Adapter<RecordDetailAdapter.ViewHolder>() {

    var items  = listOf<OrderDetailModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_record_detail, parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: OrderDetailModel) = with(itemView) {
            nameDescription.text = context.getString(R.string.blank_ssc, item.name, item.description)
            quantityPrice.text = context.getString(R.string.blank_sgx, context.getString(R.string.blank_coin, item.price), context.getString(R.string.blank_double, item.quantity))
            subTotal.text = context.getString(R.string.blank_coin, item.price*item.quantity)
        }
    }

}

