package com.quickstore.ui.useCase.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quickstore.BuildConfig
import com.quickstore.R
import com.quickstore.data.RestConstant
import com.quickstore.data.category.model.CategoryModel
import com.quickstore.util.core.glide
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var listener: ((categoryId: Long, name: String)->Unit)? = null

    var items  = mutableListOf<CategoryModel>()
        set(value) {
            field.addAll(value)
            notifyDataSetChanged()
        }

    fun cleanItems(){
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("StringFormatInvalid")
        fun bind(item: CategoryModel) = with(itemView) {

            glide(context, icon,
                BuildConfig.URL_IMAGES + RestConstant.CATEGORY + item.image + RestConstant.ALT)

            name.text = item.name

            itemContainer.setOnClickListener { listener?.invoke(item.id, item.name) }
        }
    }

    fun setOnCategoryClickListener(listener: (categoryId: Long, name: String)->Unit){
        this.listener = listener
    }

}

