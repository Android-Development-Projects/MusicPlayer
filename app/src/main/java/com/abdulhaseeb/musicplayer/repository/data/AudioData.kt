package com.abdulhaseeb.musicplayer.repository.data

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class  AudioData(
    val uri: Uri,
    val title: String,
    val name: String,
    val album: String,
    val artist: String,
    val duration: Long,
    val date: String,
    val size: Long,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Uri::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(uri, flags)
        parcel.writeString(title)
        parcel.writeString(name)
        parcel.writeString(album)
        parcel.writeString(artist)
        parcel.writeLong(duration)
        parcel.writeString(date)
        parcel.writeLong(size)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AudioData> {
        override fun createFromParcel(parcel: Parcel): AudioData {
            return AudioData(parcel)
        }

        override fun newArray(size: Int): Array<AudioData?> {
            return arrayOfNulls(size)
        }
    }

}
