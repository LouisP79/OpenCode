package com.quickstore.data.weekDayDelivery.model

import android.os.Parcel
import android.os.Parcelable

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class WeekDayDeliveryModel() : Parcelable {

    @field:JsonProperty
    var id: Int = 0

    @field:JsonProperty
    var status: Int = 0

    var isSelected: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        status = parcel.readInt()
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(status)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeekDayDeliveryModel> {
        override fun createFromParcel(parcel: Parcel): WeekDayDeliveryModel {
            return WeekDayDeliveryModel(parcel)
        }

        override fun newArray(size: Int): Array<WeekDayDeliveryModel?> {
            return arrayOfNulls(size)
        }
    }


}
