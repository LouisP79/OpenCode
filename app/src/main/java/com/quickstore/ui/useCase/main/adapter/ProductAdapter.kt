package com.quickstore.ui.useCase.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quickstore.BuildConfig
import com.quickstore.R
import com.quickstore.data.RestConstant
import com.quickstore.data.product.model.ProductModel
import com.quickstore.util.core.glide
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val LABEL = 0
        const val PRODUCT = 1
    }

    private var listener: ((product: ProductModel)->Unit)? = null

    var items  = mutableListOf<Any>()
        set(value) {
            field.addAll(value)
            notifyDataSetChanged()
        }

    fun cleanItems(){
        items.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if(items[position] is String)
            LABEL
        else PRODUCT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            LABEL -> ViewHolderLabel(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_product_label,
                    parent,
                    false
                )
            )
            else -> ViewHolderProduct(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_product,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            LABEL -> (holder as ViewHolderLabel).bind()
            else -> (holder as ViewHolderProduct).bind(items[position] as ProductModel)
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolderProduct(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ProductModel) = with(itemView) {


            glide(context, img,
                BuildConfig.URL_IMAGES + RestConstant.PRODUCT + item.image + RestConstant.ALT)

            price.text = context.getString(R.string.blank_coin, item.price)
            name.text = item.name
            description.text = item.description

            itemContainer.setOnClickListener { listener?.invoke(item) }
        }
    }

    inner class ViewHolderLabel(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(){}
    }

    fun setOnProductClickListener(listener: (product: ProductModel)->Unit){
        this.listener = listener
    }

}


