package com.quickstore.data.order.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class OrderModel() : Parcelable {

    @field:JsonProperty
    var id: Long = 0

    @field:JsonProperty
    var date: String = ""

    @field:JsonProperty("delivery_date")
    var deliveryDate: String = ""

    @field:JsonProperty("delivery_time")
    var deliveryTime: String = ""

    @field:JsonProperty("delivery_cost")
    var deliveryCost: Double = 0.0

    @field:JsonProperty("meeting_point_tag")
    var deliveryTag: String = ""

    @field:JsonProperty("meeting_point_address")
    var deliveryAddress: String = ""

    @field:JsonProperty
    var status: Int = 0

    @field:JsonProperty("status_comment")
    var statusComment: String? = null

    @field:JsonProperty("products")
    var details = listOf<OrderDetailModel>()

    @field:JsonProperty
    var total: Double = 0.0

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        date = parcel.readString()!!
        deliveryDate = parcel.readString()!!
        deliveryTime = parcel.readString()!!
        deliveryCost = parcel.readDouble()
        deliveryTag = parcel.readString()!!
        deliveryAddress = parcel.readString()!!
        status = parcel.readInt()
        statusComment = parcel.readString()!!
        details = parcel.createTypedArrayList(OrderDetailModel)!!
        total = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(date)
        parcel.writeString(deliveryDate)
        parcel.writeString(deliveryTime)
        parcel.writeDouble(deliveryCost)
        parcel.writeString(deliveryTag)
        parcel.writeString(deliveryAddress)
        parcel.writeInt(status)
        parcel.writeString(statusComment)
        parcel.writeTypedList(details)
        parcel.writeDouble(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderModel> {
        override fun createFromParcel(parcel: Parcel): OrderModel {
            return OrderModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderModel?> {
            return arrayOfNulls(size)
        }
    }


}
