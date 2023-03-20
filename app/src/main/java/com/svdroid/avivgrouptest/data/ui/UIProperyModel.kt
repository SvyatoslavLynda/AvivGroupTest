package com.svdroid.avivgrouptest.data.ui

data class UIPropertyModel(
    val id: Long,
    val bedrooms: Int?,
    val rooms: Int?,
    val offerType: Int,
    val area: Double?,
    val price: Double?,
    val url: String?,
    val city: String,
    val professional: String,
    val propertyType: String,
    val isFavorite: Boolean,
)