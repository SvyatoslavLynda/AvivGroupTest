package com.svdroid.avivgrouptest.data.ui

data class UIPropertyModel(
    val id: Int,
    val bedrooms: Int?,
    val rooms: Int?,
    val offerType: Int?,
    val area: Float?,
    val price: Float?,
    val url: String?,
    val city: String?,
    val professional: String?,
    val propertyType: String?,
    val isFavorite: Boolean,
)