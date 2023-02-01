package com.quickstore.data.order.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class OrderDetailModel() : Parcelable {

    @field:JsonProperty
    var description: String = ""

    @field:JsonProperty
    var image: String = ""

    @field:JsonProperty
    var name: String = ""

    @field:JsonProperty
    var price: Double = 0.0

    @field:JsonProperty
    var quantity: Double = 0.0

    @field:JsonProperty("sub_total_per_product")
    var subTotal: Double = 0.0

    constructor(parcel: Parcel) : this() {
        description = parcel.readString()!!
        image = parcel.readString()!!
        name = parcel.readString()!!
        price = parcel.readDouble()
        quantity = parcel.readDouble()
        subTotal = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeDouble(quantity)
        parcel.writeDouble(subTotal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetailModel> {
        override fun createFromParcel(parcel: Parcel): OrderDetailModel {
            return OrderDetailModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetailModel?> {
            return arrayOfNulls(size)
        }
    }

}
