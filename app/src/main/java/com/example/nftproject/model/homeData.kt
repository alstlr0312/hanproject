package com.example.nftproject.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class homeData(var name: String? = null, var movieImage: Int ) : Parcelable {
    val TAG: String = "로그"

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    init{
        Log.d(TAG,"Homecall")
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(movieImage)
    }

    companion object CREATOR : Parcelable.Creator<homeData> {
        override fun createFromParcel(parcel: Parcel): homeData {
            return homeData(parcel)
        }

        override fun newArray(size: Int): Array<homeData?> {
            return arrayOfNulls(size)
        }
    }

}