package com.svdroid.avivgrouptest.data.repository.source.network

import com.svdroid.avivgrouptest.data.api.PropertiesApiService
import com.svdroid.avivgrouptest.data.api.PropertiesListResponse
import com.svdroid.avivgrouptest.data.api.PropertyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PropertiesNetworkSourceImpl @Inject constructor(
    private val apiService: PropertiesApiService,
) : PropertiesNetworkSource {

    override suspend fun getPropertiesList(): PropertiesListResponse {
        return withContext(Dispatchers.IO) {
            apiService.getPropertiesList()
        }
    }

    override suspend fun getProperty(id: Long): PropertyResponse {
        return withContext(Dispatchers.IO) {
            apiService.getProperty(id)
        }
    }
}