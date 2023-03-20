package com.svdroid.avivgrouptest.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PropertiesEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "bedrooms") val bedrooms: Int?,
    @ColumnInfo(name = "rooms") val rooms: Int?,
    @ColumnInfo(name = "offer_type") val offerType: Int?,
    @ColumnInfo(name = "area") val area: Double?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "professional") val professional: String?,
    @ColumnInfo(name = "property_type") val propertyType: String?,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
)