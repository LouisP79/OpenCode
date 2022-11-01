package com.quickstore.ui.useCase.main.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.quickstore.R

class IncreaseQuantityProductDetailComponent : LinearLayout {

    private lateinit var remove: ImageView
    private lateinit var quantityTv: TextView
    private lateinit var add: ImageView
    private lateinit var subTotalTv: TextView

    var quantity: Double = 1.0
        set(value) {
            field = value
            quantityTv.text = context.getString(R.string.blank_double, field)
        }

    var subTotal: Double = 1.0
        set(value) {
            field = value
            subTotalTv.text = context.getString(R.string.blank_coin, field)
        }

    var price: Double = 1.0
        set(value) {
            field = value
            subTotalTv.text = context.getString(R.string.blank_coin, field * quantity)
        }

    var decimal: Boolean = false

    constructor(context: Context) : super(context) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setup(attrs, defStyle)
    }

    private fun setup(attrs: AttributeSet? = null, defStyle: Int = -1) {
        // Creamos la interfaz a partir del layout
        val view = View.inflate(context, R.layout.component_increase_quantity_product_detail, this)

        remove = view.findViewById(R.id.remove)
        quantityTv = view.findViewById(R.id.quantity)
        add = view.findViewById(R.id.add)
        subTotalTv = view.findViewById(R.id.subTotal)

        remove.setOnClickListener { funRemove() }
        add.setOnClickListener { funAdd() }

        init(attrs, defStyle)

    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun init(attrs: AttributeSet?, defStyle: Int) {

        if (attrs == null) return

        val attributes = if (defStyle > 0)
            context.obtainStyledAttributes(attrs, R.styleable.IncreaseQuantityProductDetailComponent, defStyle, 0)
        else context.obtainStyledAttributes(attrs, R.styleable.IncreaseQuantityProductDetailComponent)


        quantity = attributes.getFloat(R.styleable.IncreaseQuantityProductDetailComponent_quantity, quantity.toFloat()).toDouble()
        subTotal = attributes.getFloat(R.styleable.IncreaseQuantityProductDetailComponent_subTotal, subTotal.toFloat()).toDouble()
        price = attributes.getFloat(R.styleable.IncreaseQuantityProductDetailComponent_price, price.toFloat()).toDouble()
        decimal = attributes.getBoolean(R.styleable.IncreaseQuantityProductDetailComponent_decimal, decimal)

        attributes.recycle()

    }

    private fun funAdd() {
        if(decimal)
            quantity += 0.50
        else quantity++
        subTotal = quantity * price
    }

    private fun funRemove() {
        if(decimal) {
            if (quantity > 0.5)
                quantity -= 0.50
            else quantity = 0.50
        }else {
            if(quantity > 1)
                quantity--
            else quantity = 1.0
        }
        subTotal = quantity * price
    }

}