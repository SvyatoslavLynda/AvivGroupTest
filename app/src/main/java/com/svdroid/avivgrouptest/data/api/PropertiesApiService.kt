package com.svdroid.avivgrouptest.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface PropertiesApiService {

    @GET("/listings.json")
    suspend fun getPropertiesList(): PropertiesListResponse

    @GET("/listings/{property_id}.json")
    suspend fun getPropertyList(@Path("property_id") propertyId: Int): PropertyResponse
}