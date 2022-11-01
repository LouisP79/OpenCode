package com.quickstore.ui.useCase.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quickstore.R
import com.quickstore.data.cart.model.CartItemsModel
import com.quickstore.ui.useCase.main.component.ItemCartComponent
import kotlinx.android.synthetic.main.item_shopping_cart.view.*

class ShoppingCartAdapter: RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    var cartListener: CartListener? = null

    var items  = mutableListOf<CartItemsModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun cleanItems(){
        items.clear()
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int): Boolean{
        items.removeAt(position)
        return if(items.isEmpty()) true
        else {
            notifyDataSetChanged()
            false
        }
    }

    fun confirmAddSubtract(quantityAddedSubtracted: Double, position: Int, ejecution: Boolean){
        if(ejecution) items[position].quantity += quantityAddedSubtracted
        else items[position].quantity -= quantityAddedSubtracted
        items[position].total = items[position].quantity * items[position].product.price
        items[position].loading = false
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_cart, parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position)
    override fun getItemCount() = items.size

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: CartItemsModel, position: Int) = with(itemView) {

            itemCart.imgSrc = item.product.image
            itemCart.name = item.product.name
            itemCart.description = item.product.description
            itemCart.priceCart = item.product.price
            itemCart.quantityCart = item.quantity
            itemCart.subTotalCart = item.total

            itemCart.itemCartListener = object: ItemCartComponent.ItemCartListener{
                override fun onDelete() {cartListener?.onDelete(item.product.id, position)}
                override fun onAdd(quantityAdded: Double) {
                    cartListener?.let {
                        item.loading = true
                        notifyItemChanged(position)
                        it.onAdd(item.product.id, quantityAdded, position)
                    }
                }
                override fun onSubtract(quantitySubtracted: Double) {
                    cartListener?.let {
                        item.loading = true
                        notifyItemChanged(position)
                        it.onSubtract(item.product.id, quantitySubtracted, position)
                    }
                }
            }

            itemCart.lineVisibility = position != items.size-1
            itemCart.itemLoadingVisibility = item.loading
        }
    }

    interface CartListener {
        fun onDelete(productId: Long, position: Int)
        fun onAdd(productId: Long, quantityAdded: Double, position: Int)
        fun onSubtract(productId: Long, quantitySubtracted: Double, position: Int)
    }

}

