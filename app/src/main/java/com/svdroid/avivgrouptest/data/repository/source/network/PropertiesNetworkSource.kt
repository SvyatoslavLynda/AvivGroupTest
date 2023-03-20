package com.svdroid.avivgrouptest.data.repository.source.network

import com.svdroid.avivgrouptest.data.api.PropertiesListResponse
import com.svdroid.avivgrouptest.data.api.PropertyResponse

interface PropertiesNetworkSource {

    suspend fun getPropertiesList(): PropertiesListResponse

    suspend fun getProperty(id: Long): PropertyResponse
}