package com.quickstore.ui.useCase.main.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.quickstore.BuildConfig
import com.quickstore.R
import com.quickstore.data.RestConstant
import com.quickstore.util.core.glide
import com.quickstore.util.core.showAlertDialogControl
import kotlinx.android.synthetic.main.component_item_cart.view.*

class ItemCartComponent : LinearLayout {

    var itemCartListener: ItemCartListener? = null

    private lateinit var img: ImageView
    private lateinit var delete: ImageView
    private lateinit var nameTv: TextView
    private lateinit var descriptionTv: TextView
    private lateinit var priceTv: TextView
    private lateinit var remove: ImageView
    private lateinit var quantityTv: TextView
    private lateinit var add: ImageView
    private lateinit var subTotalTv: TextView

    var imgSrc: String = ""
        set(value) {
            field = value
            glide(context, img,
                BuildConfig.URL_IMAGES + RestConstant.PRODUCT + field + RestConstant.ALT)
        }

    var name: String = ""
        set(value) {
            field = value
            nameTv.text = field
        }

    var description: String = ""
        set(value) {
            field = value
            descriptionTv.text = field
        }

    var quantityCart: Double = 1.0
        set(value) {
            field = value
            quantityTv.text = context.getString(R.string.blank_double, field)
        }

    var priceCart: Double = 1.0
        set(value) {
            field = value
            priceTv.text = context.getString(R.string.blank_coin, field)
            subTotalTv.text = context.getString(R.string.blank_coin, field * quantityCart)
        }

    var subTotalCart: Double = 1.0
        set(value) {
            field = value
            subTotalTv.text = context.getString(R.string.blank_coin, field)
        }

    var decimalCart: Boolean = false

    var lineVisibility: Boolean = true
        set(value) {
            field = value
            if(field) line.visibility = View.VISIBLE else line.visibility = View.GONE
        }

    var itemLoadingVisibility: Boolean = true
        set(value) {
            field = value
            if(field) itemLoading.visibility = View.VISIBLE else itemLoading.visibility = View.GONE
        }

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
        val view = View.inflate(context, R.layout.component_item_cart, this)

        img = view.findViewById(R.id.img)
        delete = view.findViewById(R.id.delete)
        nameTv = view.findViewById(R.id.name)
        descriptionTv = view.findViewById(R.id.description)
        priceTv = view.findViewById(R.id.price)
        remove = view.findViewById(R.id.remove)
        quantityTv = view.findViewById(R.id.quantity)
        add = view.findViewById(R.id.add)
        subTotalTv = view.findViewById(R.id.subTotal)

        delete.setOnClickListener { itemCartListener?.let {
            showAlertDialogControl(context!!,
                R.string.delete_product_cart_alert_title, R.string.delete_product_cart_alert_msg
            ) {
                it.onDelete()
            }
        } }

        remove.setOnClickListener {
            val result = funSubtract()
            if(result != -1.0) itemCartListener?.onSubtract(result)
        }
        add.setOnClickListener { itemCartListener?.onAdd(funAdd()) }

        init(attrs, defStyle)

    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun init(attrs: AttributeSet?, defStyle: Int) {

        if (attrs == null) return

        val attributes = if (defStyle > 0)
            context.obtainStyledAttributes(attrs, R.styleable.ItemCartComponent, defStyle, 0)
        else context.obtainStyledAttributes(attrs, R.styleable.ItemCartComponent)


        name = attributes.getString(R.styleable.ItemCartComponent_name) ?: name
        description = attributes.getString(R.styleable.ItemCartComponent_description) ?: description
        quantityCart = attributes.getFloat(R.styleable.ItemCartComponent_quantityCart, quantityCart.toFloat()).toDouble()
        subTotalCart = attributes.getFloat(R.styleable.ItemCartComponent_subTotalCart, subTotalCart.toFloat()).toDouble()
        priceCart = attributes.getFloat(R.styleable.ItemCartComponent_priceCart, priceCart.toFloat()).toDouble()
        decimalCart = attributes.getBoolean(R.styleable.ItemCartComponent_decimalCart, decimalCart)

        attributes.recycle()

    }

    private fun funAdd(): Double {
        return if(decimalCart) {
            0.50
        }else {
            1.0
        }
    }

    private fun funSubtract(): Double {
        var result = -1.0
        if(decimalCart) {
            if (quantityCart > 0.5)
                result = 0.50
        }else {
            if(quantityCart > 1)
                result = 1.0
        }
        return result
    }

    interface ItemCartListener {
        fun onDelete()
        fun onAdd(quantityAdded: Double)
        fun onSubtract(quantitySubtracted: Double)
    }

}