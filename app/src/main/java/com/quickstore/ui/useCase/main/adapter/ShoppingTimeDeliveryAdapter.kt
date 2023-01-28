package com.quickstore.ui.useCase.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.quickstore.R
import com.quickstore.data.timeDelivery.model.TimeDeliveryModel
import kotlinx.android.synthetic.main.item_delivery_time.view.*

class ShoppingTimeDeliveryAdapter: RecyclerView.Adapter<ShoppingTimeDeliveryAdapter.ViewHolder>() {

    private var listener: ((timeDelivery: String)->Unit)? = null

    var items  = listOf<TimeDeliveryModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_delivery_time, parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position)
    override fun getItemCount() = items.size

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: TimeDeliveryModel, position: Int) = with(itemView) {

            deliveryTime.text = item.timeDelivery
            deliveryTime.setOnClickListener{
                changeSelected(position)
                listener?.invoke(item.timeDelivery)
            }
            if(item.isSelected){
                deliveryTime.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                deliveryTime.setBackgroundResource(R.drawable.shape_button_blue)
            }else{
                deliveryTime.setTextColor(ContextCompat.getColor(context, R.color.colorShapeSpinner))
                deliveryTime.setBackgroundResource(R.drawable.shape_button_white)
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

    fun setOnTimeDeliveryClickListener(listener: (timeDelivery: String)->Unit){
        this.listener = listener
    }

}

