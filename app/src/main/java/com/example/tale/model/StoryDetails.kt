package com.example.tale.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp



class StoryDetails() : Parcelable {
    private var time:Timestamp? = null

    private var url:String? = null

    constructor(parcel: Parcel) : this() {
        time = parcel.readParcelable(Timestamp::class.java.classLoader)
        url = parcel.readString()
    }

    //private var uid:String? = null

   // fun getuid():String?{
     //   return uid
    //}

    fun gettime(): Timestamp?{
        return time
    }

    fun geturl(): String? {
        return url
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(time, flags)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoryDetails> {
        override fun createFromParcel(parcel: Parcel): StoryDetails {
            return StoryDetails(parcel)
        }

        override fun newArray(size: Int): Array<StoryDetails?> {
            return arrayOfNulls(size)
        }
    }
}