package com.quickstore.ui.useCase.main.model

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

class Suggestion(var value: String = "") : SearchSuggestion {

    constructor(parcel: Parcel) : this() {
        value = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int)  = parcel.writeString(value)
    override fun getBody(): String  = value
    override fun describeContents(): Int  = 0

    companion object CREATOR : Parcelable.Creator<Suggestion> {
        override fun createFromParcel(parcel: Parcel): Suggestion {
            return Suggestion(parcel)
        }

        override fun newArray(size: Int): Array<Suggestion?> {
            return arrayOfNulls(size)
        }
    }
}