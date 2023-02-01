package com.quickstore.ui.useCase.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.quickstore.R
import com.quickstore.data.order.model.OrderModel
import kotlinx.android.synthetic.main.item_record.view.*

class RecordAdapter: RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    private var listener: ((order: OrderModel)->Unit)? = null

    var items  = mutableListOf<OrderModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun cleanItems(){
        items.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: OrderModel) = with(itemView) {
            date.text = item.date
            addressTag.text = item.deliveryTag
            orderNumber.text = context.getString(R.string.order_number,item.id)
            productsCount.text = context.getString(R.string.product_number,item.details.size)
            total.text = context.getString(R.string.blank_coin, item.total)
            when(item.status){
                0->{
                    orderStatus.text = context.getString(R.string.pending)
                    orderStatus.setTextColor(ContextCompat.getColor(context, R.color.colorItemBottomNavigationView))
                    orderStatus.setBackgroundResource(R.drawable.shape_order_pending)
                }
                1->{
                    orderStatus.text = context.getString(R.string.passed)
                    orderStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    orderStatus.setBackgroundResource(R.drawable.shape_order_passed)
                }
                2->{
                    orderStatus.text = context.getString(R.string.delivered)
                    orderStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    orderStatus.setBackgroundResource(R.drawable.shape_order_delivered)
                }
                3->{
                    orderStatus.text = context.getString(R.string.canceled)
                    orderStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    orderStatus.setBackgroundResource(R.drawable.shape_order_canceled)
                }
            }
            container.setOnClickListener { listener?.invoke(item) }
        }
    }

    fun setOnRecordClickListener(listener: (order: OrderModel)->Unit){
        this.listener = listener
    }

}

