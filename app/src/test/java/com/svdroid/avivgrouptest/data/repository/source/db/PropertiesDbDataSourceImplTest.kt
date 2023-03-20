package com.svdroid.avivgrouptest.data.repository.source.db

import com.nhaarman.mockitokotlin2.whenever
import com.svdroid.avivgrouptest.data.db.PropertiesDao
import com.svdroid.avivgrouptest.data.db.PropertiesEntity
import com.svdroid.avivgrouptest.data.mapper.toUIFilters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PropertiesDbDataSourceImplTest {
    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private lateinit var propertiesDao: PropertiesDao
    private lateinit var dataSource: PropertiesDbDataSourceImpl

    @Before
    fun setUp() {
        propertiesDao = mock(PropertiesDao::class.java)
        dataSource = PropertiesDbDataSourceImpl(propertiesDao)
    }

    @Test
    fun testGetProperties() {
        val properties =
            listOf(PropertiesEntity(1, 2, 3, 4, 5.0, 6.0, "url", "city", "professional", "propertyType", false))

        testScope.launch {
            whenever(propertiesDao.getProperties()).thenReturn(flowOf(properties))

            val result = dataSource.getProperties()

            assertEquals(properties, result.first())
        }
    }

    @Test
    fun testGetFavoriteProperties() {
        val properties =
            listOf(PropertiesEntity(1, 2, 3, 4, 5.0, 6.0, "url", "city", "professional", "propertyType", true))

        testScope.launch {
            whenever(propertiesDao.getFavoriteProperties()).thenReturn(flowOf(properties))

            val result = dataSource.getFavoriteProperties()

            assertEquals(properties, result.first())
        }
    }

    @Test
    fun testGetProperty() {
        val property = PropertiesEntity(1, 2, 3, 4, 5.0, 6.0, "url", "city", "professional", "propertyType", false)

        testScope.launch {
            whenever(propertiesDao.getProperty(1)).thenReturn(flowOf(property))

            val result = dataSource.getProperty(1)

            assertEquals(property, result.first())
        }
    }

    @Test
    fun testGetFiltersRanges() {
        val properties = listOf(
            PropertiesEntity(1, 2, 3, 4, 5.0, 6.0, "url", "city", "professional", "propertyType", false),
            PropertiesEntity(2, 10, null, 4, 125.0, 1116.0, "url", "city", "professional", "propertyType", false),
            PropertiesEntity(3, 8, 1, 4, 125.0, 1116.0, "url", "city", "professional", "propertyType", false),
            PropertiesEntity(4, null, null, 4, null, 1.0, "url", "city", "professional", "propertyType", false),
        )
        val expected = properties.toUIFilters()

        testScope.launch {
            whenever(propertiesDao.getProperties()).thenReturn(flowOf(properties))

            val result = dataSource.getFiltersRanges()

            assertEquals(expected, result)
        }
    }
}