package com.dilara.spacex.model.upcoming_launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class UpcomingLaunches (
    val id:String?,
    val name:String?,
    @SerializedName("date_utc")
    val date:String?,
    @SerializedName("flight_number")
    val flightNumber:Int,
    val links:Links
):Parcelable