package com.svdroid.avivgrouptest.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface PropertiesApiService {

    @GET("/listings.json")
    suspend fun getPropertiesList(): PropertiesListResponse

    @GET("/listings/{id}.json")
    suspend fun getProperty(@Path("id") id: Long): PropertyResponse
}