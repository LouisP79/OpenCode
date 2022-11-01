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
    var image : String = ""

    @field:JsonProperty
    var name: String = ""

    @field:JsonProperty
    var price: Double = 0.0

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        description = parcel.readString()!!
        image = parcel.readString()!!
        name = parcel.readString()!!
        price = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeDouble(price)
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
