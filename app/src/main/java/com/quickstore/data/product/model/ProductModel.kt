package com.quickstore.data.product.model

import android.os.Parcel
import android.os.Parcelable

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ProductModel() : Parcelable {

    @field:JsonProperty
    var id: Long = 0

    @field:JsonProperty
    var description: String = ""

    @field:JsonProperty
    var images = listOf<String>()

    @field:JsonProperty
    var sku: String = ""

    @field:JsonProperty
    var name: String = ""

    @field:JsonProperty
    var price: Double = 0.0

    @field:JsonProperty
    var outstanding: Boolean = false

    @field:JsonProperty
    var status: Boolean = false

    @field:JsonProperty
    var stock: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        description = parcel.readString()!!
        images = parcel.createStringArrayList()!!
        sku = parcel.readString()!!
        name = parcel.readString()!!
        price = parcel.readDouble()
        outstanding = parcel.readByte() != 0.toByte()
        status = parcel.readByte() != 0.toByte()
        stock = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(description)
        parcel.writeStringList(images)
        parcel.writeString(sku)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeByte(if (outstanding) 1 else 0)
        parcel.writeByte(if (status) 1 else 0)
        parcel.writeByte(if (stock) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }


}
