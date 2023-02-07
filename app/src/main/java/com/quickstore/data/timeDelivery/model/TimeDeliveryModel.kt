package com.quickstore.data.timeDelivery.model

import android.os.Parcel
import android.os.Parcelable

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class TimeDeliveryModel() : Parcelable {

    @field:JsonProperty
    var id: Long = 0

    @field:JsonProperty("time_delivery")
    var timeDelivery: String = ""

    @field:JsonProperty("id_week_day_delivery")
    var idWeekDayDelivery: Long = 0

    var isSelected: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        timeDelivery = parcel.readString()!!
        idWeekDayDelivery = parcel.readLong()
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(timeDelivery)
        parcel.writeLong(idWeekDayDelivery)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TimeDeliveryModel> {
        override fun createFromParcel(parcel: Parcel): TimeDeliveryModel {
            return TimeDeliveryModel(parcel)
        }

        override fun newArray(size: Int): Array<TimeDeliveryModel?> {
            return arrayOfNulls(size)
        }
    }


}
