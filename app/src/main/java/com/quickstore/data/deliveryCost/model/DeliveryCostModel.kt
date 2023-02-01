package com.quickstore.data.deliveryCost.model

import android.os.Parcel
import android.os.Parcelable

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class DeliveryCostModel() : Parcelable {

    @field:JsonProperty
    var cost: Double = 0.0

    constructor(parcel: Parcel) : this() {
        cost = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(cost)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeliveryCostModel> {
        override fun createFromParcel(parcel: Parcel): DeliveryCostModel {
            return DeliveryCostModel(parcel)
        }

        override fun newArray(size: Int): Array<DeliveryCostModel?> {
            return arrayOfNulls(size)
        }
    }

}
