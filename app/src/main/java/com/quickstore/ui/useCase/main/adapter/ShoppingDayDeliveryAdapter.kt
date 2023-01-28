package com.quickstore.ui.useCase.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.quickstore.R
import com.quickstore.ui.useCase.main.model.DateDeliveryModel
import kotlinx.android.synthetic.main.item_delivery_date.view.*

class ShoppingDayDeliveryAdapter: RecyclerView.Adapter<ShoppingDayDeliveryAdapter.ViewHolder>() {

    private var listener: ((dateDelivery: String)->Unit)? = null

    var items  = listOf<DateDeliveryModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_delivery_date, parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position)
    override fun getItemCount() = items.size

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: DateDeliveryModel, position: Int) = with(itemView) {

            deliveryDate.text = item.date
            deliveryDate.setOnClickListener{
                changeSelected(position)
                listener?.invoke(item.date)
            }
            if(item.isSelected){
                deliveryDate.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                deliveryDate.setBackgroundResource(R.drawable.shape_button_blue)
            }else{
                deliveryDate.setTextColor(ContextCompat.getColor(context, R.color.colorShapeSpinner))
                deliveryDate.setBackgroundResource(R.drawable.shape_button_white)
            }
        }
    }

    private fun changeSelected(position: Int) {
        for(item in items){
            item.isSelected = false
        }
        items[position].isSelected = true
        notifyDataSetChanged()
    }

    fun setOnDateDeliveryClickListener(listener: (dateDelivery: String)->Unit){
        this.listener = listener
    }

}

