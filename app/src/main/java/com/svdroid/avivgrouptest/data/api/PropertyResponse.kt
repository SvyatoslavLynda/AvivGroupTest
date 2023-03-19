package com.svdroid.avivgrouptest.data.api

data class PropertyResponse(
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
)