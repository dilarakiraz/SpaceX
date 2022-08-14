package com.dilara.spacex.model.upcoming_launches

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links (
    val patch:Patch
        ):Parcelable