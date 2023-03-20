package com.svdroid.avivgrouptest.data.repository.source.network

import com.nhaarman.mockitokotlin2.whenever
import com.svdroid.avivgrouptest.data.api.PropertiesApiService
import com.svdroid.avivgrouptest.data.api.PropertiesListResponse
import com.svdroid.avivgrouptest.data.api.PropertyResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class PropertiesNetworkSourceImplTest {
    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private lateinit var mockApiService: PropertiesApiService

    private lateinit var propertiesNetworkSource: PropertiesNetworkSourceImpl

    @Before
    fun setUp() {
        mockApiService = mock(PropertiesApiService::class.java)
        propertiesNetworkSource = PropertiesNetworkSourceImpl(mockApiService)
    }

    @Test
    fun testGetPropertiesList() {
        val expectedResponse = PropertiesListResponse(emptyList())

        testScope.launch {
            whenever(mockApiService.getPropertiesList()).thenReturn(expectedResponse)

            val actualResponse = propertiesNetworkSource.getPropertiesList()

            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun testGetProperty() {
        val id = 1L
        val expectedResponse = PropertyResponse(
            1,
            2,
            3,
            4,
            5.0,
            6.0,
            "url",
            "city",
            "professional",
            "propertyType",
        )

        testScope.launch {
            whenever(mockApiService.getProperty(id)).thenReturn(expectedResponse)

            val actualResponse = propertiesNetworkSource.getProperty(id)

            assertEquals(expectedResponse, actualResponse)
        }
    }
}
