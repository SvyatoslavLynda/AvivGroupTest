package com.svdroid.avivgrouptest.data.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UIFilters(
    val minPrice: Double,
    val maxPrice: Double,
    val minArea: Double,
    val maxArea: Double,
    val minRooms: Int,
    val maxRooms: Int,
    val minBedrooms: Int,
    val maxBedrooms: Int,
) : Parcelable