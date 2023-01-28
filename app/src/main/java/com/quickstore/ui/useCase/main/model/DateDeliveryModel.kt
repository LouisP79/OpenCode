package com.quickstore.ui.useCase.main.model

import android.os.Parcel
import android.os.Parcelable

class DateDeliveryModel(var date: String = "") : Parcelable {

    var isSelected: Boolean = false

    constructor(parcel: Parcel) : this(parcel.readString()!!) {
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateDeliveryModel> {
        override fun createFromParcel(parcel: Parcel): DateDeliveryModel {
            return DateDeliveryModel(parcel)
        }

        override fun newArray(size: Int): Array<DateDeliveryModel?> {
            return arrayOfNulls(size)
        }
    }


}
