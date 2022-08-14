package com.dilara.spacex.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName="rockets")
data class Rockets (
    @PrimaryKey(autoGenerate = true)
    var num:Int?=null,
    val id:String?,
    val name:String?,
    val description:String?,
    @SerializedName("flickr_images")
    val flickrImages:ArrayList<String>,
    @SerializedName("first_flight")
    val firstFlight:String?,
    val company:String?,
    var isLike:Boolean
    ):Parcelable{
}