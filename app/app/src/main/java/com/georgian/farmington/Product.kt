package com.georgian.farmington

import android.os.Parcel
import android.os.Parcelable


class Product(var product_name:String?= null, var description:String?= null,var image:String?= null, var quantity: String?= null, var price:String?= null):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(product_name)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(quantity)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

}