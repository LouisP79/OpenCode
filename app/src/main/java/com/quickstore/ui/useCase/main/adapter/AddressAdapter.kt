package com.quickstore.ui.useCase.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quickstore.R
import com.quickstore.data.address.model.AddressModel
import kotlinx.android.synthetic.main.item_address.view.*

class AddressAdapter: RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var deleteListener: ((idAddress: Long, posi: Int)->Unit)? = null
    private var selectedListener: ((address: AddressModel)->Unit)? = null
    private var radioVisibility = true
    private var deleteVisibility = true

    private var items  = mutableListOf<AddressModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setItemsVisibility(items: List<AddressModel>, radioVisibility: Boolean, deleteVisibility: Boolean){
        this.radioVisibility = radioVisibility
        this.deleteVisibility = deleteVisibility
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(addressModel: AddressModel){
        items.add(addressModel)
        notifyItemInserted(items.size - 1)
    }

    fun deleteItem(posi: Int){
        items.removeAt(posi)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position)
    override fun getItemCount() = items.size

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: AddressModel, posi: Int) = with(itemView) {

            addressTag.text = item.tag
            addressReference.text = context.getString(R.string.blank_sgs, item.address, item.reference)
            radio.isChecked = item.selected

            if(radioVisibility){
                radio.visibility = View.VISIBLE
                itemContainer.setOnClickListener {
                    clearSelection()
                    item.selected = true
                    notifyDataSetChanged()
                    selectedListener?.invoke(item)
                }
            } else radio.visibility = View.GONE

            if(deleteVisibility){
                delete.visibility = View.VISIBLE
                delete.setOnClickListener { deleteListener?.invoke(item.id, posi) }
            } else delete.visibility = View.GONE
        }
    }

    fun setOnDeleteClickListener(listener: (idAddress: Long, posi: Int)->Unit){
        this.deleteListener = listener
    }

    fun setOnSelectedClickListener(listener: (address: AddressModel)->Unit){
        this.selectedListener = listener
    }

    private fun clearSelection(){
        for(item in items){
            item.selected = false
        }
    }

}

