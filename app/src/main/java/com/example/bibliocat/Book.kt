package com.example.bibliocat

import android.os.Parcel
import android.os.Parcelable


data class Book(
    val id: Int = -1,
    val title: String? = "",
    val author: String? = "",
    val isbn: String? = "",
    val publisher: String? = "",
    val edition: String? = "",
    val pages: String? = "",
    val genre: String? = "",
    val year: String? = "",
    val price: String? = "",
    val rating: Double = 0.0,
    val read: Boolean = false,
    val wishlist: Boolean = false,
//    val coverImage: Blob?
):Parcelable {
//    fun getCoverImage(): Blob? {
//        return coverImage
//    }
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(isbn)
        parcel.writeString(publisher)
        parcel.writeString(edition)
        parcel.writeString(pages)
        parcel.writeString(genre)
        parcel.writeString(year)
        parcel.writeString(price)
        parcel.writeDouble(rating)
        parcel.writeByte(if (read) 1 else 0)
        parcel.writeByte(if (wishlist) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}