package com.quickstore.data.cart.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.quickstore.data.product.model.ProductModel

@JsonIgnoreProperties(ignoreUnknown = true)
class CartItemsModel() : Parcelable {

    @field:JsonProperty
    var id: Long = 0

    @field:JsonProperty
    var product = ProductModel()

    @field:JsonProperty
    var quantity: Double = 0.0

    @field:JsonProperty("sub_total_per_product")
    var subTotalPerProduct: Double = 0.0

    var loading: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        product = parcel.readParcelable(ProductModel::class.java.classLoader)!!
        quantity = parcel.readDouble()
        subTotalPerProduct = parcel.readDouble()
        loading = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeParcelable(product, flags)
        parcel.writeDouble(quantity)
        parcel.writeDouble(subTotalPerProduct)
        parcel.writeByte(if (loading) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItemsModel> {
        override fun createFromParcel(parcel: Parcel): CartItemsModel {
            return CartItemsModel(parcel)
        }

        override fun newArray(size: Int): Array<CartItemsModel?> {
            return arrayOfNulls(size)
        }
    }

}
